package com.febino.dependencies;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.febino.DatabaseManager.CopyCursor;
import com.febino.DatabaseManager.DataBaseManager;
import com.febino.aquafish.BuildConfig;
import com.febino.aquafish.MainActivity;
import com.febino.dataclass.BillDetails;
import com.febino.dataclass.OrderDetails;
import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.TraderDetails;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import static android.content.Context.MODE_PRIVATE;

public class PdfGenerator {


    private Document billDocument;
    private File targetFolder;
    private Context context;
    private float totalTareWeight = (float) 0.0;
    private int totalTareCount = 0;
    private float totalGrossWeight = (float) 0.0;
    private int totalGrossCount = 0;
    DataBaseManager dbm;
    private String fileName = "";
    private File folder;

    private CopyCursor copyCursor;

    DecimalFormat decimalFormat;

    private BillDetails billDetails;
    private ArrayList<OrderDetails> orderDetailsArrayList;
    private TraderDetails traderDetails;

    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private static final int PERMISSION_ALL = 1;

    public PdfGenerator(Context context, Activity activity, BillDetails billDetails, ArrayList<OrderDetails> orderDetailsArrayList){

        if (!ProjectUtils.hasPermission(context, ProjectUtils.STORAGE_PERMISSIONS)) {
            ActivityCompat.requestPermissions( activity, PERMISSIONS, PERMISSION_ALL);
//            Toast.makeText(context, "Allow Storage Permission", Toast.LENGTH_LONG).show();
            return;
        }

        String target;

        dbm = new DataBaseManager(context);
        copyCursor = new CopyCursor();
        this.context = context;

        this.billDetails = billDetails;
        this.orderDetailsArrayList = orderDetailsArrayList;

        decimalFormat = new DecimalFormat("#.0");

        traderDetails = copyCursor.copyTraderFromCursor(dbm.getTraderInTraderTableByID(billDetails.getTraderID()));

        this.fileName = getFileName(billDetails);

        this.fileName = fileName.replace(" ","_").replace(":","_").replace("-","_");


        billDocument = new Document();




        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            folder = new File("/sdcard/", MainActivity.APP_NAME);
        }else{
            folder = new File(context.getFilesDir(), MainActivity.APP_NAME);
        }

        try {
            if(!folder.exists())    folder.mkdirs();
        }catch (Exception e){
            e.printStackTrace();
        }

        target = folder.getPath()+"/"+this.fileName+".pdf";

        targetFolder = new File(target);

    }

    public void shareFile(File file) {
        Intent shareIntentFile = new Intent(Intent.ACTION_SEND);
        Uri uri = FileProvider.getUriForFile(Objects.requireNonNull(context), BuildConfig.APPLICATION_ID + ".provider", file);
        shareIntentFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
        shareIntentFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntentFile.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        shareIntentFile.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntentFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...");
        shareIntentFile.putExtra(Intent.EXTRA_TEXT, "Sharing File " + file.getName());
        context.startActivity(Intent.createChooser(shareIntentFile,"Share File"));
    }



    public File getTargetFolder(){
        return targetFolder;
    }

    public boolean isBillFileExist(){
        return targetFolder.exists();
    }

    public boolean deleteFile(){
        return targetFolder.delete();
    }

    public boolean generateBill() {
        return generatePDFBill();
    }

    public void shareBill(){
        generateBill();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        shareFile(getTargetFolder());
    }

    public String getFileName(BillDetails billDetails) {
        String fileName = "Bill_";
        fileName += billDetails.getBillNo() + "_";
        fileName += billDetails.getBillDate();
        return fileName;
    }



    private boolean generatePDFBill(){

        try {

            try {
                PdfWriter.getInstance(billDocument,new FileOutputStream(targetFolder));
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            billDocument.open();

            BaseFont mediumTamilFont = BaseFont.createFont("assets/fonts/notosanstamil_medium.ttf", BaseFont.IDENTITY_H, true);
//            BaseFont mediumTamilFont = BaseFont.createFont("assets/fonts/notosanstamil_medium.ttf", "UTF-8", BaseFont.EMBEDDED);
            Font head1 = FontFactory.getFont(FontFactory.HELVETICA, 24,Font.BOLD);
            Font tamilHead = new Font(mediumTamilFont, 18, Font.BOLD);

            Font head2 = FontFactory.getFont(FontFactory.HELVETICA, 18,Font.BOLD);
            Font fieldFontHead = new Font(mediumTamilFont, 12, Font.BOLD);
//            Font fieldFontHead = FontFactory.getFont(FontFactory.HELVETICA, 12,Font.BOLD);
            Font fieldFontData = new Font(mediumTamilFont, 12, Font.NORMAL);
//            Font fieldFontData = FontFactory.getFont(FontFactory.HELVETICA, 12,Font.NORMAL);


            Paragraph namePara = new Paragraph("S.P.F. டேங்க் மீன்",tamilHead);
            Paragraph addressPara = new Paragraph("Address Line 1, Address Line 2",head2);
            Paragraph contactNo = new Paragraph("Ph:9876543210", fieldFontHead);
//            Paragraph gstNo = new Paragraph("GST No:"+companyDetails.GSTno, fieldFont);


            /////////////////////////////////////////////////////////////////////////

            float[] columnWidths = new float[]{20f, 10f, 40f};


            namePara.setAlignment(Element.ALIGN_CENTER);
            namePara.setSpacingAfter(5f);

            addressPara.setAlignment(Element.ALIGN_CENTER);
            addressPara.setSpacingAfter(5f);

            contactNo.setAlignment(Element.ALIGN_CENTER);
            contactNo.setSpacingAfter(5f);

//            footerPara.setAlignment(Element.ALIGN_CENTER);
//            footerPara.setSpacingAfter(20f);

            PdfPTable billDetailsTable = new PdfPTable(3);
            billDetailsTable.setWidths(columnWidths);
            billDetailsTable.setWidthPercentage(100);


            String billDetailsString[][] = {
                    {"Name", traderDetails.name},
                    {"Bill No", "" + billDetails.getBillNo()},
                    {"Bill Date", convertDateFormat(billDetails.getBillDate())}
            };

            for (int i = 0; i < billDetailsString.length; i++) {

                Phrase head = new Phrase(billDetailsString[i][0], fieldFontHead);
                PdfPCell fieldHead = new PdfPCell(head);
                PdfPCell fieldSemicolon = new PdfPCell( new Phrase(":"));
                PdfPCell fieldValue = new PdfPCell(new Phrase(billDetailsString[i][1]));

                fieldHead.setPadding(3f);
                fieldSemicolon.setPadding(3f);
                fieldValue.setPadding(3f);

                fieldHead.setBorder(Rectangle.NO_BORDER);
                fieldSemicolon.setBorder(Rectangle.NO_BORDER);
                fieldValue.setBorder(Rectangle.NO_BORDER);

                billDetailsTable.addCell(fieldHead);
                billDetailsTable.addCell(fieldSemicolon);
                billDetailsTable.addCell(fieldValue);

            }

            billDetailsTable.setSpacingAfter(20f);



            PdfPTable orderDetailsTable = new PdfPTable(5);
            orderDetailsTable.setWidthPercentage(100);



            PdfPCell date = new PdfPCell(new Phrase("Order Date",fieldFontHead));
            PdfPCell breed = new PdfPCell( new Phrase("Breed",fieldFontHead));
            PdfPCell box = new PdfPCell(new Phrase("Box",fieldFontHead));
            PdfPCell kg = new PdfPCell(new Phrase("Kg",fieldFontHead));
//            PdfPCell rate = new PdfPCell(new Phrase("Rate",fieldFontHead));
            PdfPCell amount = new PdfPCell(new Phrase("Amount",fieldFontHead));


            date.setHorizontalAlignment(Element.ALIGN_CENTER);
            date.setPadding(3f);

            breed.setPadding(3f);
            breed.setHorizontalAlignment(Element.ALIGN_CENTER);

            box.setPadding(3f);
            box.setHorizontalAlignment(Element.ALIGN_CENTER);

            kg.setPadding(3f);
            kg.setHorizontalAlignment(Element.ALIGN_CENTER);

//            rate.setPadding(3f);
//            rate.setHorizontalAlignment(Element.ALIGN_CENTER);

            amount.setPadding(3f);
            amount.setHorizontalAlignment(Element.ALIGN_CENTER);


            orderDetailsTable.addCell(date);
            orderDetailsTable.addCell(breed);
            orderDetailsTable.addCell(box);
            orderDetailsTable.addCell(kg);
//            orderDetailsTable.addCell(rate);
            orderDetailsTable.addCell(amount);


            for(int i=0;i<orderDetailsArrayList.size();i++){


                OrderDetails orderDetails = orderDetailsArrayList.get(i);
                ProductDetails productDetails = copyCursor.copyProductFromCursor(dbm.getProductFromProductTableByID(orderDetails.getProductID()));

                date = new PdfPCell(new Phrase(convertDateFormat(orderDetails.getOrderDate()),fieldFontData));
                breed = new PdfPCell( new Phrase(productDetails.productName,fieldFontData));
                box = new PdfPCell(new Phrase(""+orderDetails.getTotalBox(),fieldFontData));
                kg = new PdfPCell(new Phrase(""+orderDetails.getTotalKG(),fieldFontData));
//                rate = new PdfPCell(new Phrase("100.0",fieldFontData));
                float calAmount = ((orderDetails.getKgPerBox() * orderDetails.getTotalBox()) + orderDetails.getTotalKG()) * orderDetails.getRatePerKG();
                amount = new PdfPCell(new Phrase(decimalFormat.format(calAmount),fieldFontData));


                date.setHorizontalAlignment(Element.ALIGN_CENTER);
                date.setPadding(3f);

                breed.setPadding(3f);
                breed.setHorizontalAlignment(Element.ALIGN_CENTER);

                box.setPadding(3f);
                box.setHorizontalAlignment(Element.ALIGN_CENTER);

                kg.setPadding(3f);
                kg.setHorizontalAlignment(Element.ALIGN_CENTER);

//                rate.setPadding(3f);
//                rate.setHorizontalAlignment(Element.ALIGN_CENTER);

                amount.setPadding(3f);
                amount.setHorizontalAlignment(Element.ALIGN_CENTER);


                orderDetailsTable.addCell(date);
                orderDetailsTable.addCell(breed);
                orderDetailsTable.addCell(box);
                orderDetailsTable.addCell(kg);
//                orderDetailsTable.addCell(rate);
                orderDetailsTable.addCell(amount);
            }

            orderDetailsTable.setSpacingAfter(20f);


            PdfPTable calculationTable = new PdfPTable(3);
            calculationTable.setWidths(columnWidths);
            calculationTable.setWidthPercentage(100);




            billDocument.add(namePara);
            billDocument.add(addressPara);
            billDocument.add(contactNo);
            billDocument.add(billDetailsTable);
            billDocument.add(orderDetailsTable);
            billDocument.add(calculationTable);
            billDocument.close();

        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;

    }

    private String getTimeBetweenString(String start, String end) {
        Date startDate = new Date();
        Date endDate = new Date();
//        Log.i("Start date", start);
//        Log.i("End date", end);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                if(isTimeFormat24hr)
//                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                else
//                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");


        try {
            startDate = simpleDateFormat.parse(start);
            endDate = simpleDateFormat.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long totalTimeTaken = endDate.getTime() - startDate.getTime();
        long secs = totalTimeTaken/1000;
        long mins = secs/60;
        long hrs = mins/60;

        if (totalTimeTaken < (60*60*1000))
            return (mins%60)+"Min "+(secs%60)+"Sec";
        else
            return (hrs%24) +"Hr "+(mins%60)+"Min";
    }


    public static String convertDateFormat(String dateStr) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");


        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {

            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;  // Return null if parsing fails
        }
    }


    public void openGeneratedBill(){

        generateBill();
        Intent intent=new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", targetFolder);
//            Uri uri = Uri.fromFile(targetFolder);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try
        {
            context.startActivity(intent);
        }
        catch(ActivityNotFoundException e)
        {
            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_LONG).show();
        }

    }

    private void toastMessage(final String message){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }




}