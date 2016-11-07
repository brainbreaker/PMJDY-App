package org.bom.india_hackaton.activities;

import android.content.Intent;
import android.os.Bundle;

import org.bom.android.container.models.banking.BankingUser;
import org.bom.india_hackaton.App;
import org.bom.india_hackaton.R;
import org.bom.india_hackaton.activities.base.BaseBankingActivity;
import org.bom.india_hackaton.activities.imt.ImtActivity;
import org.bom.india_hackaton.activities.fundtransfer.FundTransferActivity;
import org.bom.india_hackaton.utils.RxUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class BankingHomeActivity extends BaseBankingActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking_home);
        ButterKnife.bind(this);
        initializeToolbar(getString(R.string.home), false);
    }

    @OnClick(R.id.logout_button)
    void logoutButtonClicked() {
        showProgressDialog();
        App.getClientContainer().getBankingClient().logoutObservable()
                .compose(RxUtils.<BankingUser>applyDefaultSchedulers(this))
                .subscribe(new Action1<BankingUser>() {
                    @Override
                    public void call(BankingUser bankingUser) {
                        hideProgressDialog();
                        Intent intentToLaunch = new Intent(BankingHomeActivity.this, LoginActivity.class);
                        BankingHomeActivity.this.startActivity(intentToLaunch);
                        BankingHomeActivity.this.finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        hideProgressDialog();
                        showError(error);
                    }
                });
    }

    @OnClick(R.id.account_info_button)
    void accountInfoButtonClicked() {
        Intent intentToLaunch = new Intent(this, AccountInfoActivity.class);
        startActivity(intentToLaunch);
    }

    @OnClick(R.id.account_list_button)
    void accountListButtonClicked() {
        Intent intentToLaunch = new Intent(this, AccountListActivity.class);
        startActivity(intentToLaunch);
    }

    @OnClick(R.id.transaction_history_button)
    void transactionHistoryButtonClicked() {
        Intent intentToLaunch = new Intent(this, TransactionHistoryActivity.class);
        startActivity(intentToLaunch);
    }

    @OnClick(R.id.fundtransfer_button)
    void fundTransferButtonClicked() {
        Intent intentToLaunch = new Intent(this, FundTransferActivity.class);
        startActivity(intentToLaunch);
    }

    @OnClick(R.id.cashsend_button)
    void imtButtonClicked() {
        Intent intentToLaunch = new Intent(this, ImtActivity.class);
        startActivity(intentToLaunch);
    }
}
