package org.bom.india_hackaton.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.bom.android.container.models.banking.Transaction;
import org.bom.india_hackaton.App;
import org.bom.india_hackaton.R;
import org.bom.india_hackaton.activities.base.BaseBankingActivity;
import org.bom.india_hackaton.utils.RxUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class TransactionHistoryActivity extends BaseBankingActivity {
    @BindView(R.id.transaction_history_textview)
    TextView mTransactionHistoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_history);
        ButterKnife.bind(this);

        initializeToolbar("Transactions History", true);

        fetchTransactionsHistory();
    }

    private void fetchTransactionsHistory() {
        showProgressDialog();

        App.getClientContainer().getBankingClient().getTransactionsHistoryObservable()
                .compose(RxUtils.<List<Transaction>>applyDefaultSchedulers(this))
                .subscribe(new Action1<List<Transaction>>() {
                    @Override
                    public void call(List<Transaction> transactions) {
                        hideProgressDialog();
                        showTransactions(transactions);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        hideProgressDialog();
                        showError(error);
                    }
                });
    }

    private void showTransactions(List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder();
        for (Transaction transaction : transactions) {
            sb.append(App.getSharedGson().toJson(transaction)).append("\n\n");
            System.out.println("Txn History: " + App.getSharedGson().toJson(transaction));
        }
        mTransactionHistoryTextView.setText(sb.toString());
    }
}
