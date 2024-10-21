package com.febino.aquafish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.febino.DatabaseManager.CopyCursor;
import com.febino.DatabaseManager.DataBaseManager;
import com.febino.dataclass.BillDetails;
import com.febino.dataclass.OrderDetails;
import com.febino.dataclass.TraderDetails;
import com.febino.dependencies.PdfGenerator;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BillViewActivity extends AppCompatActivity {

    Button printBtn, shareBtn, viewBtn;

    public static final String BILL_ID_EXTRAS = "bill_id_string";

    float billAmount, balanceAmount, oldBalanceAmount;

    TextView billAmountText, balanceAmountText, oldBalanceAmountTxt, totalAmountText;
    TextView traderNameText, traderIDText;
    ListView billViewOrderListView;
    EditText billNoEdit, billDateEdit;
    DataBaseManager db;
    CopyCursor cc;
    BillDetails billDetails;

    DecimalFormat decimalFormat;
    TraderDetails traderDetails;
    ArrayList<OrderDetails> orderDetailsArrayList;


    long billID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            billID = extras.getLong(BILL_ID_EXTRAS, 0);
            Log.i("bill ID", ""+billID);
//            return;
        }

        db = new DataBaseManager(BillViewActivity.this);
        cc = new CopyCursor();
        decimalFormat = new DecimalFormat("#.0");

        billDetails = cc.copyBillFromCursor(db.getBillFromBillTableByID(billID));
        orderDetailsArrayList = cc.copyOrderListFromCursor(db.getOrderFromOrderTableByBillID(billID));
        traderDetails = cc.copyTraderFromCursor(db.getTraderInTraderTableByID(billDetails.getTraderID()));

        Log.i("Bill Id", "" + billID);

        printBtn = findViewById(R.id.bill_view_print_btn);
        shareBtn = findViewById(R.id.bill_view_share_btn);
        viewBtn = findViewById(R.id.bill_view_view_btn);

        traderNameText = findViewById(R.id.bill_view_name_textview);
        traderIDText = findViewById(R.id.bill_view_trader_id_textview);

        billAmountText = findViewById(R.id.bill_view_bill_amount_text);
        balanceAmountText = findViewById(R.id.bill_view_balance_text);
        oldBalanceAmountTxt = findViewById(R.id.bill_view_old_balance_text);
        totalAmountText = findViewById(R.id.bill_view_total_amount_text);

        billViewOrderListView = findViewById(R.id.bill_view_listview);

        billNoEdit = findViewById(R.id.bill_view_billno_edit);
        billDateEdit = findViewById(R.id.bill_view_date_edit);


        traderNameText.setText(traderDetails.name);
        traderIDText.setText(traderDetails.trader_id);
        billNoEdit.setText(billDetails.getBillNo()+"");
        billDateEdit.setText(billDetails.getBillDate());

        billAmount = billDetails.getBillAmount();
        balanceAmount = billDetails.getBalanceAmount();
        oldBalanceAmount = billDetails.getOldBalanceAmount();

        billAmountText.setText(billAmount+"");
        balanceAmountText.setText(balanceAmount+"");
        oldBalanceAmountTxt.setText(oldBalanceAmount+"");

        totalAmountText.setText(""+(billAmount+balanceAmount+oldBalanceAmount));


        BillGenerateOrderAdapter billGenerateOrderAdapter = new BillGenerateOrderAdapter(orderDetailsArrayList,BillViewActivity.this,db);
        billViewOrderListView.setAdapter(billGenerateOrderAdapter);


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PdfGenerator pdfGenerator = new PdfGenerator(BillViewActivity.this, BillViewActivity.this, billDetails, orderDetailsArrayList);
                pdfGenerator.shareBill();


            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfGenerator pdfGenerator = new PdfGenerator(BillViewActivity.this, BillViewActivity.this, billDetails, orderDetailsArrayList);
                pdfGenerator.openGeneratedBill();

            }
        });

    }
}