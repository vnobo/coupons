package com.alex.wx.core.wallet;

import com.alex.wx.BaseGenericController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("withdraw")
public class WithdrawController extends BaseGenericController {


    private WalletService walletService;

    public WithdrawController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("transfer/{withdrawId}")
    public Object TransferConfirmation(@PathVariable long withdrawId){
        return walletService.transfer(withdrawId);
    }
}
