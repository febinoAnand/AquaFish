package com.febino.aquafish;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.febino.DatabaseManager.CopyCursor;
import com.febino.DatabaseManager.DataBaseManager;
import com.febino.aquafish.R;
import com.febino.dataclass.BillDetails;
import com.febino.dataclass.OrderDetails;
import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.TraderDetails;
import com.febino.dependencies.PdfGenerator;
import com.febino.validation.ValidateDetails;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BillGenerateFragment extends Fragment {

    AutoCompleteTextView billName;
    EditText billDateEdit, billNoEdit;
    ImageButton addOrderImgBtn;
    TextView balanceText,oldBalanceText, billAmountText, totalAmountText;
    LinearLayout balanceLayout, oldBalanceLayout;
    Calendar mCurrentDate;
    String dateTimeFormat = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat;
    ArrayList<TraderDetails> traderDetailsArrayList;

    ArrayList<OrderDetails> selectedOrderArrayList;

    TraderDetails selectedTraderDetails = null;

    ListView billGenerateListView;
    BillGenerateOrderAdapter billGenerateOrderAdapter;

    DecimalFormat decimalFormat;

    private long updateLastBillNo;

    private double totalAmount;
    private float balanceAmount, oldBalanceAmount, billAmount;
    private Button generateBtn;

    private static final int BALANCE = 2;
    private static final int OLD_BALANCE = 1;
    DataBaseManager db;
    CopyCursor cc;

    Fragment currentFragment;

    public BillGenerateFragment(){
        currentFragment = this;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_generate_fragment,container,false);
        db = new DataBaseManager(getContext());
        cc = new CopyCursor();

        mCurrentDate = Calendar.getInstance();
        decimalFormat = new DecimalFormat("#.0");



        traderDetailsArrayList = cc.copyTraderListFromCurser(db.getAllTraderFromTraderTable());

        selectedOrderArrayList = new ArrayList<OrderDetails>();

        billAmountText = view.findViewById(R.id.bill_generate_bill_amount_text);
        balanceText = view.findViewById(R.id.bill_generete_balance_text);
        oldBalanceText = view.findViewById(R.id.bill_generete_old_balance_text);
        totalAmountText = view.findViewById(R.id.bill_generate_total_amount_text);

        balanceLayout = view.findViewById(R.id.bill_generate_balance_layout);
        oldBalanceLayout = view.findViewById(R.id.bill_generate_old_balance_layout);

        addOrderImgBtn = view.findViewById(R.id.bill_generate_add_order_imgbtn);
        billGenerateListView = view.findViewById(R.id.bill_generate_tab_listview);
        billName = view.findViewById(R.id.bill_main_name_edit);
        billNoEdit = view.findViewById(R.id.bill_main_billno_edit);
        generateBtn = view.findViewById(R.id.bill_generate_btn);

        billGenerateOrderAdapter = new BillGenerateOrderAdapter(selectedOrderArrayList, getContext(),db);
        billGenerateListView.setAdapter(billGenerateOrderAdapter);

        simpleDateFormat = new SimpleDateFormat(dateTimeFormat);

        billDateEdit = view.findViewById(R.id.bill_main_date_edit);

        updateLastBillNo = db.getLastBillNoFromBillTable()+1;

        billDateEdit.setText(simpleDateFormat.format(mCurrentDate.getTime()));
        billNoEdit.setText(""+updateLastBillNo);

        billName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = layoutInflater.inflate(R.layout.bill_trader_adapter_view, null);

                BillTraderSelectAdapter billTraderSelectAdapter = new BillTraderSelectAdapter(traderDetailsArrayList,getContext());
                ListView traderList = v.findViewById(R.id.bill_trader_select_list);
                traderList.setAdapter(billTraderSelectAdapter);

                Button cancelBtn = v.findViewById(R.id.bill_trader_select_dialog_cancel_btn);

                traderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedTraderDetails = traderDetailsArrayList.get(position);
                        selectedOrderArrayList.clear();
                        billGenerateOrderAdapter.notifyDataSetChanged();
                        billName.setText(selectedTraderDetails.name);
                        bottomSheetDialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });


                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.show();

            }
        });

        billDateEdit.setOnClickListener(new View.OnClickListener() {
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
                        billDateEdit.setText(dayString);

                        mDay[0] = selectedday;
                        mMonth[0] = selectedmonth;
                        mYear[0] = selectedyear;
                    }
                }, mYear[0], mMonth[0], mDay[0]);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        billGenerateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.dialog_order_details, null);

                OrderDetails orderDetails = selectedOrderArrayList.get(position);
                ProductDetails productDetails = new CopyCursor().copyProductFromCursor(db.getProductFromProductTableByID(orderDetails.getProductID()));
                TraderDetails traderDetails = new CopyCursor().copyTraderFromCursor(db.getTraderInTraderTableByID(orderDetails.getTraderID()));


                Button okBtn = view.findViewById(R.id.order_dialog_ok_btn);
                Button cancelBtn = view.findViewById(R.id.order_dialog_cancel_btn);

                EditText name = view.findViewById(R.id.order_name_edit);
                EditText item = view.findViewById(R.id.order_item_edit);
                EditText box = view.findViewById(R.id.order_box_edit);
                EditText kg = view.findViewById(R.id.order_kgs_edit);
                EditText rate = view.findViewById(R.id.order_rate_edit);
                EditText date = view.findViewById(R.id.order_date_edit);

                name.setFocusable(false);
                item.setFocusable(false);
                date.setFocusable(false);

                name.setText(traderDetails.name);
                item.setText(productDetails.productName);
                box.setText(""+orderDetails.getTotalBox());
                kg.setText("" + orderDetails.getTotalKG());
                rate.setText("" + orderDetails.getRatePerKG());
                date.setText("" + orderDetails.getOrderDate());
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String orderBoxString = box.getText().toString();
                        String orderKGString = kg.getText().toString();
                        String orderRateString = rate.getText().toString();
//                        String orderKGPerBoxString = "35.0"; //TODO: add 35kg/box in the settings page
                        String errorMessage = ValidateDetails.ValidateOrderDetails(getContext(),orderBoxString, orderKGString, orderRateString);

                        if (errorMessage != null) {
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                            return;
                        }


                        orderDetails.setTotalBox(Integer.parseInt(orderBoxString));
                        orderDetails.setTotalKG(Float.parseFloat(orderKGString));
                        orderDetails.setRatePerKG(Float.parseFloat(orderRateString));
//                            currentOrderDetails.setKgPerBox(Float.parseFloat(orderKGPerBoxString));
                        db.updateOrderInOrderTable(orderDetails);
                        Toast.makeText(getContext(),R.string.order_updated,Toast.LENGTH_LONG).show();
                        selectedOrderArrayList.set(position, orderDetails);
                        billGenerateOrderAdapter.notifyDataSetChanged();

                        updateBillAmount();
                        updateTotalAmount();
                        bottomSheetDialog.dismiss();

                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();

                    }
                });

                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
//                return false;
            }
        });

        balanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertBuildForBalance("Balance", balanceText, BALANCE);
            }
        });

        oldBalanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertBuildForBalance("Old Balance", oldBalanceText, OLD_BALANCE);
            }
        });

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTraderDetails == null){
                    Toast.makeText(getContext(), "Select Trader",Toast.LENGTH_LONG).show();
                    return;
                }
                if(selectedOrderArrayList.size() <= 0){
                    Toast.makeText(getContext(), "Add Orders in the Order list",Toast.LENGTH_LONG).show();
                    return;
                }
                BillDetails billDetails = new BillDetails();

                long lastBillNo = db.getLastBillNoFromBillTable();

                billDetails.setBillNo(lastBillNo+1);
                billDetails.setBillDate(billDateEdit.getText().toString());
                billDetails.setBalanceAmount(balanceAmount);
                billDetails.setOldBalanceAmount(oldBalanceAmount);
                billDetails.setBillAmount(billAmount);
                billDetails.setTraderID(selectedTraderDetails._id);

                long billID = db.addBillInBillTableReturnID(billDetails,selectedOrderArrayList);
                Intent intent = new Intent(getContext(), BillViewActivity.class);
                intent.putExtra(BillViewActivity.BILL_ID_EXTRAS, billID);
                startActivity(intent);

                PdfGenerator pdfGenerator = new PdfGenerator(getContext(),getActivity(), billDetails, selectedOrderArrayList);
                pdfGenerator.generateBill();
                getFragmentManager().beginTransaction().detach(currentFragment).attach(currentFragment).commit();
//                resetPage();
//                FragmentTransaction currentFragment = getFragmentManager().beginTransaction().replace(R.id.);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BillFragment()).commit();

            }
        });

        addOrderImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = billName.getText().toString();

                if(selectedTraderDetails == null || name == null || name.equals(null) || name.equals("")) {
                    Toast.makeText(getContext(), "Please select Name to add order", Toast.LENGTH_LONG).show();
                    return;
                }


                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.dialog_bill_add_order_details, null);

                DataBaseManager db = new DataBaseManager(getContext());
                CopyCursor cc = new CopyCursor();
                ArrayList<OrderDetails> orderDetailsArrayList = cc.copyOrderListFromCursor(db.getOrderFromOrderTableByTraderID(selectedTraderDetails._id));


                for (int i = 0; i < orderDetailsArrayList.size(); i++) {
                    for (int j = 0; j < selectedOrderArrayList.size(); j++) {
                        if(orderDetailsArrayList.get(i).get_id() == selectedOrderArrayList.get(j).get_id()){
                            orderDetailsArrayList.get(i).isSelected = true;
                        }
                    }
                }


                ListView orderListView = v.findViewById(R.id.bill_generate_order_list);
                Button okBtn = v.findViewById(R.id.bill_generate_add_dialog_ok_btn);
                Button cancelBtn = v.findViewById(R.id.bill_generate_add_dialog_cancel_btn);

                final BillOrderListSelectAdapter billOrderListSelectAdapter = new BillOrderListSelectAdapter(orderDetailsArrayList, getContext(),db);

                orderListView.setAdapter(billOrderListSelectAdapter);

                orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        OrderDetails orderDetails = orderDetailsArrayList.get(position);
                        orderDetails.isSelected = !orderDetails.isSelected;
                        billOrderListSelectAdapter.notifyDataSetChanged();

                        if(orderDetails.isSelected) selectedOrderArrayList.add(orderDetails);
                        else{
                            for(int k = 0; k < selectedOrderArrayList.size();k++){

                                if (orderDetails.get_id() == selectedOrderArrayList.get(k).get_id()) {
                                    selectedOrderArrayList.remove(k);
                                }
                            }

                        }

                    }
                });


                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateBillAmount();
                        updateTotalAmount();
                        billGenerateOrderAdapter.notifyDataSetChanged();
                        bottomSheetDialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.show();

            }
        });


        return view;
    }

    private void updateBillAmount() {
        billAmount = 0.0f;

        for (int i = 0; i < selectedOrderArrayList.size(); i++) {
            OrderDetails orderDetails = selectedOrderArrayList.get(i);
            float kg = orderDetails.getTotalKG();
            float rate = orderDetails.getRatePerKG();
            int box = orderDetails.getTotalBox();
            float boxPerKg = 35.0f;

            billAmount += (kg + (box * boxPerKg)) * rate;

        }
        billAmountText.setText("" + decimalFormat.format(billAmount));

    }

    private void updateTotalAmount(){
        totalAmount = billAmount + balanceAmount + oldBalanceAmount;
        totalAmountText.setText("" + decimalFormat.format(totalAmount));

    }

    private void showAlertBuildForBalance(String title, TextView setTextView, int updateValue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText oldBalaceEdit = new EditText(getContext());
        oldBalaceEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(oldBalaceEdit);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String balanceString = oldBalaceEdit.getText().toString();

                float amount = 0.0f;

                try {
                    amount = Float.parseFloat(balanceString);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Enter valid Input", Toast.LENGTH_LONG).show();
                    return;
                }

                if(updateValue == BALANCE) {
                    balanceAmount = amount;
                } else if (updateValue == OLD_BALANCE) {
                    oldBalanceAmount = amount;
                }
                setTextView.setText(decimalFormat.format(amount));
                updateTotalAmount();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        builder.show();
    }

    public void resetPage(){
        selectedTraderDetails = null;
        updateLastBillNo = db.getLastBillNoFromBillTable()+1;

        billName.setText("");
        billNoEdit.setText("" + updateLastBillNo);

        selectedOrderArrayList.clear();
        billGenerateOrderAdapter.notifyDataSetChanged();

        balanceAmount = 0;
        oldBalanceAmount = 0;

        balanceText.setText("" + balanceAmount);
        oldBalanceText.setText(""+oldBalanceAmount);

        updateBillAmount();
        updateTotalAmount();

    }
}
