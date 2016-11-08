package org.bom.india_hackaton.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.bom.android.container.models.banking.BankAccount;
import org.bom.india_hackaton.App;
import org.bom.india_hackaton.R;
import org.bom.india_hackaton.activities.base.BaseBankingActivity;
import org.bom.india_hackaton.utils.RxUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class AccountInfoActivity extends BaseBankingActivity {
    @BindView(R.id.accountinfo_textview)
    TextView mAccountInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        ButterKnife.bind(this);

        initializeToolbar("Account Info", true);

        fetchAccountInfo();
    }

    private void fetchAccountInfo() {
        showProgressDialog();

        App.getClientContainer().getBankingClient().getAccountInfoObservable()
                .compose(RxUtils.<BankAccount>applyDefaultSchedulers(this))
                .subscribe(new Action1<BankAccount>() {
                    @Override
                    public void call(BankAccount bankAccount) {
                        hideProgressDialog();
                        Log.d("Account info",App.getSharedGson().toJson(bankAccount));
                        mAccountInfoTextView.setText(App.getSharedGson().toJson(bankAccount));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        hideProgressDialog();
                        showError(error);
                    }
                });
    }
}
