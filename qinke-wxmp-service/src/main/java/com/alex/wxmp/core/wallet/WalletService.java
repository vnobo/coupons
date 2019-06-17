package com.alex.wxmp.core.wallet;

import cn.hutool.core.util.ObjectUtil;
import com.alex.wxmp.AbstractGenericService;
import com.alex.wxmp.core.MailService;
import com.alex.wxmp.core.SMSService;
import com.alex.wxmp.core.wallet.beans.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
public class WalletService extends AbstractGenericService {

    private WalletRepository walletRepository;
    private WithdrawRepository withdrawRepository;
    private MailService mailService;
    private SMSService smsService;

    public WalletService(WalletRepository walletRepository,
                         WithdrawRepository withdrawRepository,
                         MailService mailService,
                         SMSService smsService) {
        this.walletRepository = walletRepository;
        this.withdrawRepository = withdrawRepository;
        this.mailService = mailService;
        this.smsService = smsService;
    }

    /**
     * 管理员确认提现申请通知
     *
     * @param id 提现申请单
     */
    public Withdraw transfer(long id) {
        Optional<Withdraw> optionalWithdraw = this.withdrawRepository.findById(id);
        if (optionalWithdraw.isPresent()) {
            Withdraw withdraw = optionalWithdraw.get();
            if (withdraw.getStatus() != 0) {
                throw new WithdrawException(500, "提现申请订单状态不正确");
            }
            withdraw.setStatus(100);
            withdraw = this.withdrawRepository.save(withdraw);

            this.smsService.withdrawSuccess(withdraw.getAlipay(), withdraw.getBalance());

            return withdraw;
        } else {
            throw new WithdrawException(500, "提现申请订单不存在!");
        }
    }

    /**
     * 提取现金
     */
    @Transactional
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

        withdraw = this.withdrawRepository.save(withdraw);

        this.mailService.withdrawNotice(withdraw.getId(), withdraw.getAliname(), withdraw.getAlipay(), withdrawAmount);

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
        return this.walletRepository.count(QWallet.wallet.promoter.equalsIgnoreCase(openid));
    }

    public Wallet loadByOpenId(String openid) {
        logger.debug("loadByOpenId wallet  open id is " + openid);
        Optional<Wallet> optional = walletRepository.findByOpenid(openid);
        return optional.orElse(null);
    }

    /**
     * 增加指定用户余额
     */
    @Transactional
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
    @Transactional
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
        return walletRepository.saveAndFlush(wallet);
    }

    @Async
    public Future<Wallet> asyncSave(Wallet wallet) {
        return new AsyncResult<>(save(wallet));
    }
}
