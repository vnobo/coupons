package com.alex.wx.core.wallet;

import cn.hutool.core.util.ObjectUtil;
import com.alex.wx.BaseGenericService;
import com.alex.wx.core.wallet.beans.Wallet;
import com.alex.wx.core.wallet.beans.WalletRepository;
import com.alex.wx.core.wallet.beans.Withdraw;
import com.alex.wx.core.wallet.beans.WithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class WalletService extends BaseGenericService {

    private final WalletRepository walletRepository;
    private final WithdrawRepository withdrawRepository;


    /**
     * 管理员确认提现申请通知
     *
     * @param id 提现申请单
     */
    public Withdraw transfer(long id) {
        Optional<Withdraw> optionalWithdraw =this.withdrawRepository.findById(id);
        throw new WithdrawException(500, "提现申请订单不存在!");
    }

    /**
     * 提取现金
     */
    public Wallet withdraw(String openId) {

        Wallet wallet = this.loadByOpenId(openId);
        if (ObjectUtil.isNull(wallet)) {
            throw new WalletException(500, "你要提款的钱包不存在!");
        }
        double withdrawAmount = wallet.getBalance();

        if (withdrawAmount < 5) {
            throw new WalletException(501, "账户余额小于5元,无法提现");
        }

        // 更新累计收益
        wallet.setTotalBalance(wallet.getTotalBalance() + withdrawAmount);

        //更新当前账户余额
        wallet.setBalance(0);

        wallet.getExtend().withArray("logs").insertPOJO(0, Map.of(
                "action", "withdraw",
                "time", LocalDateTime.now(),
                "content", "申请提现: " + withdrawAmount,
                "amount", withdrawAmount));

        wallet = this.save(wallet);

        Withdraw withdraw = Withdraw.of(openId, wallet.getAliname(), wallet.getAlipay(), withdrawAmount);

        //withdraw = this.withdrawRepository.save(withdraw);

        // this.mailService.withdrawNotice(withdraw.getId(), withdraw.getAliname(), withdraw.getAlipay(), withdrawAmount);

        return wallet;
    }

    /**
     * 用户钱包是否存在
     * 不存在为false,存在为true
     */
    public boolean exists(String openid) {
        logger.debug("exists wallet  open id is " + openid);
        Wallet wallet = loadByOpenId(openid);
        return !ObjectUtils.isEmpty(wallet);
    }


    public long getSumShardUser(String openid) {
        return 0; //this.walletRepository.count(QWallet.wallet.promoter.equalsIgnoreCase(openid));
    }

    public Wallet loadByOpenId(String openid) {
        logger.debug("loadByOpenId wallet  open id is " + openid);
        Optional<Wallet> optional = walletRepository.findByOpenid(openid);
        return optional.orElse(null);
    }

    /**
     * 增加指定用户余额
     */
    public void plusBalance(String orderId, String openId, double commission) {

        Wallet wallet = loadByOpenId(openId);
        if (ObjectUtil.isNotNull(wallet)) {
            // 用户实际返利金额
            double balance = commission * wallet.getRate();

            // 更新账户余额
            wallet.setBalance(wallet.getBalance() + balance);

            this.logger.info("{} wallets plus balance {} ", openId, balance);

            wallet.getExtend().withArray("logs").insertPOJO(0, Map.of(
                    "action", "plus_balance",
                    "time", LocalDateTime.now(),
                    "content", "账户余额收益: " + balance,
                    "amount", balance,
                    "tradeId", orderId,
                    "commission", commission));

            wallet = save(wallet);

            // 判断是否有 父级
            if (!StringUtils.isEmpty(wallet.getPromoter())) {
                plusBrokerage(orderId, wallet.getPromoter(), commission);
            }

        }

    }

    /**
     * 增加指定用户收益余额
     */
    public void plusBrokerage(String orderId, String openId, double commission) {

        Wallet wallet = loadByOpenId(openId);

        if (ObjectUtil.isNotNull(wallet)) {
            // 用户实际佣金金额
            double brokerage = commission * wallet.getBrokerageRate();

            // 更新账户余额
            wallet.setBrokerage(wallet.getBrokerage() + brokerage);

            // 更新账户总收益
            wallet.setBalance(wallet.getBalance() + brokerage);

            wallet.getExtend().withArray("logs").insertPOJO(0, Map.of(
                    "action", "plus_brokerage",
                    "time", LocalDateTime.now(),
                    "content", "获得分享收益: " + brokerage,
                    "amount", brokerage,
                    "tradeId", orderId,
                    "commission", commission));

            this.logger.info("{} plus Brokerage {} ", openId, brokerage);

            save(wallet);
        }
    }


    public Wallet save(Wallet wallet) {
        return wallet;
    }

    @Async
    public Future<Wallet> asyncSave(Wallet wallet) {
        return new AsyncResult<>(save(wallet));
    }
}