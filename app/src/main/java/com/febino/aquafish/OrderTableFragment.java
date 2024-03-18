package com.febino.aquafish;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.febino.aquafish.R;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class OrderTableFragment extends Fragment implements HorizontalScroll.ScrollViewListener, VerticalScroll.ScrollViewListener{
    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    RelativeLayout relativeLayoutMain;

    RelativeLayout relativeLayoutA;
    RelativeLayout relativeLayoutB;
    RelativeLayout relativeLayoutC;
    RelativeLayout relativeLayoutD;
    RelativeLayout relativeLayoutE;
    RelativeLayout relativeLayoutF;

    TableLayout tableLayoutA;
    TableLayout tableLayoutB;
    TableLayout tableLayoutC;
    TableLayout tableLayoutD;
    TableLayout tableLayoutE;
    TableLayout tableLayoutF;

    TableRow tableRow;
    TableRow tableRowB;
    TableRow tableRowF;

    HorizontalScroll horizontalScrollViewB;
    HorizontalScroll horizontalScrollViewD;
    HorizontalScroll horizontalScrollViewF;

    VerticalScroll scrollViewC;
    VerticalScroll scrollViewD;

    /*
         This is for counting how many columns are added in the row.
    */
    int tableColumnCountB= 0;
    int tableColumnCountF= 0;

    /*
         This is for counting how many row is added.
    */
    int tableRowCountC= 0;


    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater layoutInflater1, ViewGroup viewGroup, Bundle bundle){
        View view = layoutInflater1.inflate(R.layout.order_adapter_viewpager, viewGroup, false);
//        View view = layoutInflater1.inflate(R.layout.order_adapter_viewpager, new TableMainLayout(getContext()), true);


        relativeLayoutMain= (RelativeLayout)view.findViewById(R.id.order_adapter_viewpager_layout);
        getScreenDimension();
        initializeRelativeLayout();
        initializeScrollers();
        initializeTableLayout();
        horizontalScrollViewB.setScrollViewListener(this);
        horizontalScrollViewD.setScrollViewListener(this);
        horizontalScrollViewF.setScrollViewListener(this);
        scrollViewC.setScrollViewListener(this);
        scrollViewD.setScrollViewListener(this);
        addRowToTableA();
        initializeRowForTableB();

        /*
            Till Here.
         */


        /*  There is two unused functions
            Have a look on these functions and try to recreate and use it.
            createCompleteColumn();
            createCompleteRow();
        */
        for(int i=0; i<10; i++){
            addColumnsToTableB("Head" + i, i);
        }
        for(int i=0; i<25; i++){
            initializeRowForTableD(i);
            addRowToTableC("Row"+ i);
            for(int j=0; j<tableColumnCountB; j++){
                addColumnToTableAtD(i, "D "+ i + " " + j);
            }
        }

        addRowToTableE();

        initializeRowForTableF();
        for(int i=0; i<10; i++){
            addColumnsToTableF("Total" + i, i);
        }

        return view;
    }


    private void getScreenDimension(){
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH= size.x;
        SCREEN_HEIGHT = size.y;
        Log.i("Screen_X", ""+size.x);
        Log.i("Screen_Y", ""+size.y);


    }

    private void initializeRelativeLayout(){
        relativeLayoutA= new RelativeLayout(getContext());
        relativeLayoutA.setId(R.id.relativeLayoutA);
        relativeLayoutA.setPadding(0,0,0,0);

        relativeLayoutB= new RelativeLayout(getContext());
        relativeLayoutB.setId(R.id.relativeLayoutB);
        relativeLayoutB.setPadding(0,0,0,0);

        relativeLayoutC= new RelativeLayout(getContext());
        relativeLayoutC.setId(R.id.relativeLayoutC);
        relativeLayoutC.setPadding(0,0,0,0);

        relativeLayoutD= new RelativeLayout(getContext());
        relativeLayoutD.setId(R.id.relativeLayoutD);
        relativeLayoutD.setPadding(0,0,0,0);

        relativeLayoutE = new RelativeLayout(getContext());
        relativeLayoutE.setId(R.id.relativeLayoutE);
        relativeLayoutE.setPadding(0, 0, 0, 0);

        relativeLayoutF = new RelativeLayout(getContext());
        relativeLayoutF.setId(R.id.relativeLayoutF);
        relativeLayoutF.setPadding(0, 0, 0, 0);


        relativeLayoutA.setLayoutParams(new RelativeLayout.LayoutParams(SCREEN_WIDTH/5,SCREEN_HEIGHT/20));
        this.relativeLayoutMain.addView(relativeLayoutA);

        RelativeLayout.LayoutParams layoutParamsRelativeLayoutB= new RelativeLayout.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT/20);
        layoutParamsRelativeLayoutB.addRule(RelativeLayout.RIGHT_OF, R.id.relativeLayoutA);
        relativeLayoutB.setLayoutParams(layoutParamsRelativeLayoutB);
        this.relativeLayoutMain.addView(relativeLayoutB);

        RelativeLayout.LayoutParams layoutParamsRelativeLayoutC= new RelativeLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT - (10*SCREEN_WIDTH/20));
        layoutParamsRelativeLayoutC.addRule(RelativeLayout.BELOW, R.id.relativeLayoutA);
        layoutParamsRelativeLayoutC.addRule(RelativeLayout.ABOVE, R.id.relativeLayoutE);
        relativeLayoutC.setLayoutParams(layoutParamsRelativeLayoutC);
        this.relativeLayoutMain.addView(relativeLayoutC);

        RelativeLayout.LayoutParams layoutParamsRelativeLayoutD= new RelativeLayout.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT - (10*SCREEN_WIDTH/20));
        layoutParamsRelativeLayoutD.addRule(RelativeLayout.BELOW, R.id.relativeLayoutB);
        layoutParamsRelativeLayoutD.addRule(RelativeLayout.ABOVE, R.id.relativeLayoutF);
        layoutParamsRelativeLayoutD.addRule(RelativeLayout.RIGHT_OF, R.id.relativeLayoutC);
        relativeLayoutD.setLayoutParams(layoutParamsRelativeLayoutD);
        this.relativeLayoutMain.addView(relativeLayoutD);

        //Newly added for E and F
        RelativeLayout.LayoutParams layoutParamsRelativeLayoutE= new RelativeLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        layoutParamsRelativeLayoutE.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        
//        layoutParamsRelativeLayoutE.addRule(RelativeLayout.BELOW, R.id.relativeLayoutC);
        relativeLayoutE.setLayoutParams(layoutParamsRelativeLayoutE);
        this.relativeLayoutMain.addView(relativeLayoutE);

        RelativeLayout.LayoutParams layoutParamsRelativeLayoutF = new RelativeLayout.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT/20);
        layoutParamsRelativeLayoutF.addRule(RelativeLayout.ALIGN_TOP,R.id.relativeLayoutE);
//        layoutParamsRelativeLayoutF.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParamsRelativeLayoutF.addRule(RelativeLayout.RIGHT_OF, R.id.relativeLayoutE);
//        layoutParamsRelativeLayoutF.addRule(RelativeLayout.BELOW, R.id.relativeLayoutD);
        relativeLayoutF.setLayoutParams(layoutParamsRelativeLayoutF);
        this.relativeLayoutMain.addView(relativeLayoutF);

    }

    private void initializeScrollers(){
        horizontalScrollViewB= new HorizontalScroll(getContext());
        horizontalScrollViewB.setPadding(0,0,0,0);


        horizontalScrollViewD= new HorizontalScroll(getContext());
        horizontalScrollViewD.setPadding(0,0,0,0);

        //Newly added for E and F
        horizontalScrollViewF= new HorizontalScroll(getContext());
        horizontalScrollViewF.setPadding(0,0,0,0);
        //////

        scrollViewC= new VerticalScroll(getContext());
        scrollViewC.setPadding(0,0,0,0);

        scrollViewD= new VerticalScroll(getContext());
        scrollViewD.setPadding(0,0,0,0);



        horizontalScrollViewB.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH/5), SCREEN_HEIGHT/20));
        scrollViewC.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH/5 ,SCREEN_HEIGHT - (2*SCREEN_HEIGHT/20)));
        scrollViewD.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT - (2*SCREEN_HEIGHT/20) ));
        horizontalScrollViewD.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH- (SCREEN_WIDTH/5), SCREEN_HEIGHT - (2*SCREEN_HEIGHT/20) ));

        //Newly added for E and F
        horizontalScrollViewF.setLayoutParams(new ViewGroup.LayoutParams(SCREEN_WIDTH - (SCREEN_WIDTH/5), SCREEN_HEIGHT/20));
        ///////

        this.relativeLayoutB.addView(horizontalScrollViewB);

        this.relativeLayoutC.addView(scrollViewC);
        this.scrollViewD.addView(horizontalScrollViewD);
        this.relativeLayoutD.addView(scrollViewD);

        //Newly added for E and F
        this.relativeLayoutF.addView(horizontalScrollViewF);
        ///////
    }

    private  void initializeTableLayout(){
        tableLayoutA= new TableLayout(getContext());
        tableLayoutA.setPadding(0,0,0,0);
        tableLayoutB= new TableLayout(getContext());
        tableLayoutB.setPadding(0,0,0,0);
        tableLayoutB.setId(R.id.tableLayoutB);
        tableLayoutC= new TableLayout(getContext());
        tableLayoutC.setPadding(0,0,0,0);
        tableLayoutD= new TableLayout(getContext());
        tableLayoutD.setPadding(0,0,0,0);
        //Newly added for E and F
        tableLayoutE= new TableLayout(getContext());
        tableLayoutE.setPadding(0,0,0,0);


        tableLayoutF= new TableLayout(getContext());
        tableLayoutF.setPadding(0,0,0,0);
        tableLayoutF.setId(R.id.tableLayoutF);
        /////

        TableLayout.LayoutParams layoutParamsTableLayoutA= new TableLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableLayoutA.setLayoutParams(layoutParamsTableLayoutA);
        tableLayoutA.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.relativeLayoutA.addView(tableLayoutA);

        TableLayout.LayoutParams layoutParamsTableLayoutB= new TableLayout.LayoutParams(SCREEN_WIDTH -(SCREEN_WIDTH/5), SCREEN_HEIGHT/20);
        tableLayoutB.setLayoutParams(layoutParamsTableLayoutB);
        tableLayoutB.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.horizontalScrollViewB.addView(tableLayoutB);

        TableLayout.LayoutParams layoutParamsTableLayoutC= new TableLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT - (2*SCREEN_HEIGHT/20));
        tableLayoutC.setLayoutParams(layoutParamsTableLayoutC);
        tableLayoutC.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.scrollViewC.addView(tableLayoutC);

        TableLayout.LayoutParams layoutParamsTableLayoutD= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        tableLayoutD.setLayoutParams(layoutParamsTableLayoutD);
        this.horizontalScrollViewD.addView(tableLayoutD);

        //Newly added for E and F
        TableLayout.LayoutParams layoutParamsTableLayoutE= new TableLayout.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableLayoutE.setLayoutParams(layoutParamsTableLayoutE);
        tableLayoutE.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.relativeLayoutE.addView(tableLayoutE);

        TableLayout.LayoutParams layoutParamsTableLayoutF= new TableLayout.LayoutParams(SCREEN_WIDTH -(SCREEN_WIDTH/5), SCREEN_HEIGHT/20);
        tableLayoutF.setLayoutParams(layoutParamsTableLayoutF);
        tableLayoutF.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.horizontalScrollViewF.addView(tableLayoutF);
//        this.relativeLayoutF.addView(tableLayoutF);

        //////

    }

    @Override
    public void onScrollChanged(HorizontalScroll scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView == horizontalScrollViewB){
            horizontalScrollViewD.scrollTo(x,y);
            //Newly added for E and F
            horizontalScrollViewF.scrollTo(x, y);
            ////
        } else if (scrollView == horizontalScrollViewF) {
            horizontalScrollViewB.scrollTo(x, y);
            horizontalScrollViewD.scrollTo(x, y);
        }
        else if(scrollView == horizontalScrollViewD){
            horizontalScrollViewB.scrollTo(x, y);
            //Newly added for E and F
            horizontalScrollViewF.scrollTo(x, y);
            ////
        }
    }

    @Override
    public void onScrollChanged(VerticalScroll scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView == scrollViewC){
            scrollViewD.scrollTo(x,y);
        }
        else if(scrollView == scrollViewD){
            scrollViewC.scrollTo(x,y);
        }
    }

    private void addRowToTableA(){
        tableRow= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getContext());
        label_date.setText("Item/ID");
        tableRow.setGravity(Gravity.CENTER);
//        tableRow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow.addView(label_date);
        this.tableLayoutA.addView(tableRow);
    }

    private void initializeRowForTableB(){
        tableRowB= new TableRow(getContext());
        tableRow.setPadding(0,0,0,0);
        this.tableLayoutB.addView(tableRowB);
    }

    private synchronized void addColumnsToTableB(String text, final int id){
        tableRow= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow.setPadding(3,3,3,4);
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        this.tableRow.addView(label_date);
        this.tableRow.setGravity(Gravity.CENTER);
        this.tableRow.setTag(id);
        this.tableRowB.addView(tableRow);
        tableColumnCountB++;
    }

    private synchronized void addRowToTableC(String text){
        TableRow tableRow1= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow1= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow1.setPadding(3,3,3,4);
        tableRow1.setGravity(Gravity.CENTER);
        tableRow1.setLayoutParams(layoutParamsTableRow1);
        TextView label_date = new TextView(getContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow1.addView(label_date);

        TableRow tableRow= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow.setPadding(0,0,0,0);
        tableRow.setLayoutParams(layoutParamsTableRow);
        tableRow.setGravity(Gravity.CENTER);
        tableRow.addView(tableRow1);
        this.tableLayoutC.addView(tableRow, tableRowCountC);
        tableRowCountC++;
    }

    private synchronized void initializeRowForTableD(int pos){
        TableRow tableRowB= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, SCREEN_HEIGHT/20);
        tableRowB.setPadding(0,0,0,0);
        tableRowB.setLayoutParams(layoutParamsTableRow);
        this.tableLayoutD.addView(tableRowB, pos);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private synchronized void addColumnToTableAtD(final int rowPos, String text){
        final String tempText = text;
        TableRow tableRowAdd= (TableRow) this.tableLayoutD.getChildAt(rowPos);
        tableRow= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow.setPadding(3,3,3,4);
        tableRow.setBackground(getResources().getDrawable(R.drawable.cell_bacground));
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow.setTag(label_date);
        this.tableRow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        this.tableRow.addView(label_date);
        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),tempText,Toast.LENGTH_SHORT).show();
            }
        });

        tableRowAdd.addView(tableRow);
    }



    private void addRowToTableE(){
        tableRow= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow.setLayoutParams(layoutParamsTableRow);
        TextView label_date = new TextView(getContext());
        label_date.setText("Total");
        tableRow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        tableRow.addView(label_date);
        this.tableLayoutE.addView(tableRow);
    }

    private void initializeRowForTableF(){
        tableRowF= new TableRow(getContext());
        tableRowF.setPadding(0,0,0,0);
        this.tableLayoutF.addView(tableRowF);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private synchronized void addColumnsToTableF(String text, final int id){
        tableRow= new TableRow(getContext());
        TableRow.LayoutParams layoutParamsTableRow= new TableRow.LayoutParams(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        tableRow.setPadding(3,3,3,4);
        tableRow.setLayoutParams(layoutParamsTableRow);
//        tableRow.setBackground(getResources().getDrawable(R.drawable.cell_bacground));
        TextView label_date = new TextView(getContext());
        label_date.setText(text);
        label_date.setTextSize(getResources().getDimension(R.dimen.cell_text_size));
        this.tableRow.addView(label_date);
        this.tableRow.setTag(id);
        this.tableRow.setGravity(Gravity.CENTER);
        this.tableRowF.addView(tableRow);
        tableColumnCountF++;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createCompleteColumn(String value){
        int i=0;
        int j=tableRowCountC-1;
        for(int k=i; k<=j; k++){
            addColumnToTableAtD(k, value);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createCompleteRow(String value){
        initializeRowForTableD(0);
        int i=0;
        int j=tableColumnCountB-1;
        int pos= tableRowCountC-1;
        for(int k=i; k<=j; k++){
            addColumnToTableAtD(pos, value);
        }
    }
}
