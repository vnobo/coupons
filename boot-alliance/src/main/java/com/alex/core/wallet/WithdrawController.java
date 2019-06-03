package com.alex.core.wallet;

import com.alex.AbstractGenericController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("withdraw")
public class WithdrawController extends AbstractGenericController {


    private WalletService walletService;

    public WithdrawController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("transfer/{withdrawId}")
    public Object TransferConfirmation(@PathVariable long withdrawId){
        return walletService.transfer(withdrawId);
    }
}
