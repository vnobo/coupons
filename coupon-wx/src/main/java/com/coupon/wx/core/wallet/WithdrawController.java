package com.coupon.wx.core.wallet;

import com.coupon.wx.BaseGenericController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("withdraw")
public class WithdrawController extends BaseGenericController {


    private WalletService walletService;

    public WithdrawController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("transfer/{withdrawId}")
    public Object TransferConfirmation(@PathVariable long withdrawId) {
        return walletService.transfer(withdrawId);
    }
}