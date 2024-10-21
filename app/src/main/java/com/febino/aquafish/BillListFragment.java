package com.febino.aquafish;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.febino.DatabaseManager.CopyCursor;
import com.febino.DatabaseManager.DataBaseManager;
import com.febino.aquafish.R;
import com.febino.dataclass.BillDetails;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BillListFragment extends Fragment {

    Calendar mCurrentDate;
    String dateTimeFormat = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat;

    ImageButton leftArrowImageBtn, rightArrowImageBtn;

    EditText billListDateSelect;

    ListView billListView;

    TextView totalBillCountText, totalBillAmountText;

    ArrayList<BillDetails> billDetailsArrayList;

    DataBaseManager db;
    CopyCursor cc;

    BillListViewAdapater billListViewAdapater;

    String currentDateString;
    DecimalFormat decimalFormat;
    float totalBillAmount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateListFragment(){
        updateBillDetailsArrayList(currentDateString);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_list_fragment, container, false);

        db = new DataBaseManager(getContext());

        cc = new CopyCursor();

        mCurrentDate = Calendar.getInstance();

        decimalFormat = new DecimalFormat("#.0");

        simpleDateFormat = new SimpleDateFormat(dateTimeFormat);

        currentDateString = simpleDateFormat.format(mCurrentDate.getTime()).toString();

        billListDateSelect = view.findViewById(R.id.bill_list_date_edit);
        billListDateSelect.setText(currentDateString);

        leftArrowImageBtn = view.findViewById(R.id.bill_list_calender_left);
        rightArrowImageBtn = view.findViewById(R.id.bill_list_calender_right);

        billListView = view.findViewById(R.id.bill_list_listview);

        totalBillAmountText = view.findViewById(R.id.bill_list_amount_txt);
        totalBillCountText = view.findViewById(R.id.bill_list_billcount_txt);

        updateBillDetailsArrayList(currentDateString);

//        Log.i("current date", "" + currentDateString);
//        billDetailsArrayList = cc.copyBillListFromCursor(db.getBillFromBillTableByDate(currentDateString));
//        billListViewAdapater = new BillListViewAdapater(getContext(), billDetailsArrayList);
//        billListView.setAdapter(billListViewAdapater);

        leftArrowImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate.add(Calendar.DATE, -1);
                String dayString = simpleDateFormat.format(mCurrentDate.getTime());
                billListDateSelect.setText(dayString);
                updateBillDetailsArrayList(dayString);
                //update the data in the listview
            }
        });

        rightArrowImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate.add(Calendar.DATE, 1);
                String dayString = simpleDateFormat.format(mCurrentDate.getTime());
                billListDateSelect.setText(dayString);
                updateBillDetailsArrayList(dayString);
                //update the data in the listview
            }
        });


        billListDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] mYear = {mCurrentDate.get(Calendar.YEAR)};
                final int[] mMonth = {mCurrentDate.get(Calendar.MONTH)};
                final int[] mDay = {mCurrentDate.get(Calendar.DAY_OF_MONTH)};

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(),R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);

                        mCurrentDate = myCalendar;
                        String dayString = simpleDateFormat.format(mCurrentDate.getTime());
                        billListDateSelect.setText(dayString);
                        updateBillDetailsArrayList(dayString);

                        mDay[0] = selectedday;
                        mMonth[0] = selectedmonth;
                        mYear[0] = selectedyear;
                    }
                }, mYear[0], mMonth[0], mDay[0]);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        billListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent billReviewIntent = new Intent(getContext(), BillViewActivity.class);
                long bill_id =  billDetailsArrayList.get(position).get_id();
                billReviewIntent.putExtra(BillViewActivity.BILL_ID_EXTRAS,bill_id);
                startActivity(billReviewIntent);
            }
        });



        return view;
    }

    public void updateBillDetailsArrayList(String date) {
        billDetailsArrayList = cc.copyBillListFromCursor(db.getBillFromBillTableByDate(date));
        billListViewAdapater = new BillListViewAdapater(getContext(), billDetailsArrayList,db);
        billListView.setAdapter(billListViewAdapater);

        totalBillAmount = 0f;

        for (int i = 0; i < billDetailsArrayList.size(); i++) {
            totalBillAmount += billDetailsArrayList.get(i).getBillAmount();
        }

        totalBillAmountText.setText(totalBillAmount != 0 ? decimalFormat.format(totalBillAmount) : "0");
        totalBillCountText.setText("" + billDetailsArrayList.size());

//        billListViewAdapater.notifyDataSetChanged();
    }
}
