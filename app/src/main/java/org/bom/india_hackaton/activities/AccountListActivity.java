package org.bom.india_hackaton.activities;

import android.os.Bundle;
import android.widget.TextView;

import org.bom.android.container.models.banking.BankAccount;
import org.bom.india_hackaton.App;
import org.bom.india_hackaton.R;
import org.bom.india_hackaton.activities.base.BaseBankingActivity;
import org.bom.india_hackaton.utils.RxUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class AccountListActivity extends BaseBankingActivity {
    @BindView(R.id.accountlist_textview)
    TextView mAccountListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        ButterKnife.bind(this);

        initializeToolbar("Accounts List", true);

        fetchAccountsList();
    }

    private void fetchAccountsList() {
        showProgressDialog();

        App.getClientContainer().getBankingClient().getAccountsListObervable()
                .compose(RxUtils.<List<BankAccount>>applyDefaultSchedulers(this))
                .subscribe(new Action1<List<BankAccount>>() {
                    @Override
                    public void call(List<BankAccount> bankAccounts) {
                        hideProgressDialog();
                        showAccounts(bankAccounts);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        hideProgressDialog();
                        showError(error);
                    }
                });
    }

    private void showAccounts(List<BankAccount> accounts) {
        StringBuilder sb = new StringBuilder();
        for (BankAccount account : accounts)
            sb.append(App.getSharedGson().toJson(account)).append("\n\n");

        mAccountListTextView.setText(sb.toString());
    }
}

