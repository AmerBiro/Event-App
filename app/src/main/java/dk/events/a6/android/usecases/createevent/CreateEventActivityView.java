package dk.events.a6.android.usecases.createevent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import dk.events.a6.R;
import dk.events.a6.android._Context;
import dk.events.a6.android.MainApplication;
import dk.events.a6.android.usecases.createevent.fsm.BasePrepareEventFSM;
import dk.events.a6.android.usecases.createevent.fsm.PrepareEventFSMWrapper;
import dk.events.a6.databinding.ActivityCreateBinding;
import dk.events.a6.fragments.ChooseImageDialogFragment;
import dk.eventslib.entities.Event;
import dk.eventslib.gatewayimpl.EventGatewayFirebaseImpl;
import dk.eventslib.usecases.CreateEventProcessObservable;
import dk.eventslib.usecases.CreateEventProcessObserver;
import dk.eventslib.usecases.createevent.CreateEventOutputPort;
import dk.eventslib.usecases.createevent.CreateEventUseCaseImpl;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.entities.ImageDetails;

import static dk.events.a6.android.usecases.createevent.fsm.PrepareEventStateImpl.NO_TITLE_IMG_DESC;

public class CreateEventActivityView extends AppCompatActivity implements BasePrepareEventFSM.PrepareEventFSMActions, View.OnClickListener, CreateEventOutputPort, ChooseImageDialogFragment.DialogListener {

    private Button buttonCreateEvent;
    private EditText editTextTitle;
    private CreateEventViewModel vm;
    private CreateEventUseCaseImpl useCase;
    private EditText editTextDescription;
    private Button buttonAddImageCreate;
    public static final int RESULT_LOAD_IMG = 0;
    public static final int RESULT_TAKE_A_PICTURE = 1;
    private ImageView imageViewEventImage;
    private FragmentTransaction fragmentTransaction;
    private ChooseImageDialogFragment dialogFragment;
    private Uri imageUriFromCamera;
    private ProgressBar progressBarCreateEvent;
    private ImageDetails imageDetails;
    private TextView textViewDateAndTime;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void show(String msg) {
        showMsg(msg, this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.buttonCreateEvent){
            //vm.title = editTextTitle.getText().toString();
            //vm.description = editTextDescription.getText().toString();
            fsm.createEventPressed();

            //simulate back pressed
            //onBackPressed();

        }else if(v.getId() == R.id.buttonAddImageCreate){
            dialogFragment = new ChooseImageDialogFragment();

            Bundle bundle = new Bundle();
            bundle.putBoolean("notAlertDialog", true);

            dialogFragment.setArguments(bundle);

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                fragmentTransaction.remove(prev);
            }
            fragmentTransaction.addToBackStack(null);


            dialogFragment.show(fragmentTransaction, "dialog");
        }else if(v.getId() == R.id.event_date_time){
            showDateTimeDialog(binding.line);
        }

    }

    private void setViewEnable(boolean enable) {
        buttonCreateEvent.setEnabled(enable);
        editTextTitle.setEnabled(enable);
        editTextDescription.setEnabled(enable);
        buttonAddImageCreate.setEnabled(enable);
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        showMsg("resultCode="+ resultCode + ", reqCode=" + reqCode, CreateEventActivityView.this);

            try {
                if (resultCode == RESULT_OK && reqCode ==  RESULT_LOAD_IMG){
                    handleEventPictureFromGallery(data);
                    fsm.yesImg();
                }else if(resultCode == RESULT_OK && reqCode ==  RESULT_TAKE_A_PICTURE){
                    handleEventPictureFromCamera(data);
                    fsm.yesImg();
                }else {
                    showMsg("You haven't picked Image", CreateEventActivityView.this);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                showMsg("Something went wrong", CreateEventActivityView.this);
            }finally {
               closeFragment();
            }
    }

    private void handleEventPictureFromCamera(Intent data) throws FileNotFoundException {
        //final Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
        final InputStream imageStream = getContentResolver().openInputStream(imageUriFromCamera);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        //https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        int size = selectedImage.getRowBytes() * selectedImage.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        selectedImage.copyPixelsToBuffer(byteBuffer);

        imageDetails = new ImageDetails();
        imageDetails.setHeight(selectedImage.getHeight());
        imageDetails.setWidth(selectedImage.getWidth());
        imageDetails.setConfigName(selectedImage.getConfig().name());
        imageDetails.setPixels(byteArray);

        //
        //Bitmap.Config configBmp = Bitmap.Config.valueOf(bitmap.getConfig().name());
        //Bitmap bitmap_tmp = Bitmap.createBitmap(width, height, configBmp);
        //ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        //bitmap_tmp.copyPixelsFromBuffer(buffer);

        imageViewEventImage.setImageBitmap(selectedImage);
    }

    private void handleEventPictureFromGallery(Intent data) throws FileNotFoundException {
        final Uri imageUri = data.getData();
        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        //https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        int size = selectedImage.getRowBytes() * selectedImage.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        selectedImage.copyPixelsToBuffer(byteBuffer);


        imageDetails = new ImageDetails();
        imageDetails.setHeight(selectedImage.getHeight());
        imageDetails.setWidth(selectedImage.getWidth());
        imageDetails.setConfigName(selectedImage.getConfig().name());
        imageDetails.setPixels(byteArray);



        //
        //Bitmap.Config configBmp = Bitmap.Config.valueOf(bitmap.getConfig().name());
        //Bitmap bitmap_tmp = Bitmap.createBitmap(width, height, configBmp);
        //ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        //bitmap_tmp.copyPixelsFromBuffer(buffer);
        //imageViewEventImage.setImageBitmap(bitmap_tmp);

        imageViewEventImage.setImageBitmap(selectedImage);


        //if image check needed
        //if(imageViewEventImage.getDrawable().getConstantState() == getDrawable( R.drawable.ic_baseline_image_24).getConstantState()){}
    }

    private void closeFragment() {
        getSupportFragmentManager().popBackStack();
    }

    private void showMsg(String msg, CreateEventActivityView context) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    private ActivityCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);



        //may needed in MainActivity
        MainApplication mainApplication = ((MainApplication)getApplication());

        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));
/*
        binding.eventDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(binding.line);
            }
        });*/

        vm = new CreateEventViewModel();

        initialize();

        buttonCreateEvent.setOnClickListener(this);
        buttonAddImageCreate.setOnClickListener(this);
        textViewDateAndTime.setOnClickListener(this);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        fsm.actionsImpl = this;
        fsm.setState(NO_TITLE_IMG_DESC);
        fsm.startPrepareEvent();
    }
    public PrepareEventFSMWrapper fsm = new PrepareEventFSMWrapper();
    @Override
    public void DoSetupPrepareEvent() {
        buttonAddImageCreate.setFocusable(true);
        buttonAddImageCreate.setFocusableInTouchMode(true);
        buttonAddImageCreate.setError("Image required");

        buttonCreateEvent.setEnabled(false);

        editTextTitle.setHint("title");
        editTextTitle.setError("Title required");
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(!TextUtils.isEmpty(editTextTitle.getText().toString().trim())){
                    fsm.yesTitle();
                }else {
                    fsm.noTitle();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextDescription.setHint("description");
        editTextDescription.setError("Description required");
        editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(!TextUtils.isEmpty(editTextDescription.getText().toString().trim())){
                    fsm.yesDesc();
                }else {
                    fsm.noDesc();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //imageViewEventImage.setImageDrawable(null);
        //imageViewEventImage.setTag(R.drawable.ic_baseline_image_24);
        imageViewEventImage.setImageResource(R.drawable.ic_baseline_image_24);


    }

    @Override
    public void DoCreateEvent(){
        useCase = new CreateEventUseCaseImpl();
        EventGateway gateway = new EventGatewayFirebaseImpl();
        ((CreateEventProcessObservable)gateway).addCreateEventProcessObserver(new CreateEventProcessObserver() {
            @Override
            public void starting() {
                new Handler(Looper.getMainLooper()).post(()->{
                    progressBarCreateEvent.setVisibility(View.VISIBLE);
                    progressBarCreateEvent.setProgress(1);

                    setViewEnable(false);
                });
            }
            @Override
            public void pending() {
                new Handler(Looper.getMainLooper()).post(()->{
                    progressBarCreateEvent.setProgress(3);
                });
            }
            @Override
            public void onSuccess(Event event) {
                new Handler(Looper.getMainLooper()).post(()->{
                    System.out.println("Event: " + event.toString());
                    progressBarCreateEvent.setProgress(4);

                    new Handler().postDelayed(()->{
                        progressBarCreateEvent.setVisibility(View.GONE);
                        //simulate back pressed
                        showMsg("Success in creation of Event: "+ event.toString(),CreateEventActivityView.this);
                        setViewEnable(true);
                        onBackPressed();

                    },500);
                });
            }

            @Override
            public void onFailure(Event event) {
                new Handler(Looper.getMainLooper()).post(()->{
                    System.out.println("Event: " + event.toString());
                    progressBarCreateEvent.setProgress(4);

                    new Handler().postDelayed(()->{
                        progressBarCreateEvent.setVisibility(View.GONE);
                        //simulate back pressed
                        setViewEnable(true);
                        showMsg("Failure to create Event: "+ event.toString(),CreateEventActivityView.this);
                    },500);
                });
            }
        });

        useCase.setEventGateway(gateway);
        useCase.setOutputPort(this);

        CreateEventController createEventController = new CreateEventController(useCase);

        createEventController.createEvent(vm, _Context.bruceAlmighty.getLoggedInUser());
    }

    @Override
    public void DoTitleProvided() {
        vm.title = editTextTitle.getText().toString();
    }

    @Override
    public void DoTitleRemoved() {
        editTextTitle.setError("Title required");
        vm.title = null;
    }

    @Override
    public void DoDescProvided() {
        vm.description = editTextDescription.getText().toString();
    }

    @Override
    public void DoDescRemoved() {
        editTextDescription.setError("Description required"); //maybe not needed every time?
        vm.description = null;
    }

    @Override
    public void DoImgProvided() {
        vm.createEventImages.add(imageDetails);
        buttonAddImageCreate.setError(null);
    }

    @Override
    public void DoImgRemoved() {

    }



    @Override
    public void DoEnableCreateEvent() {
        buttonCreateEvent.setEnabled(true);
    }

    @Override
    public void DoDisableCreateEvent() {
        buttonCreateEvent.setEnabled(false);
    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(CreateEventActivityView.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(CreateEventActivityView.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void initialize() {
        buttonCreateEvent = findViewById(R.id.buttonCreateEvent);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddImageCreate = findViewById(R.id.buttonAddImageCreate);
        imageViewEventImage = findViewById(R.id.imageViewEventImage);
        progressBarCreateEvent = findViewById(R.id.progressBarCreateEvent);

        textViewDateAndTime = findViewById(R.id.event_date_time);
    }

    @Override
    public void onChooseImageFromGalleryClicked() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onTakeAPictureClicked() {
        //https://stackoverflow.com/questions/10377783/low-picture-image-quality-when-capture-from-camera
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, UUID.randomUUID().toString());
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUriFromCamera = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        startActivityForResult(intent, RESULT_TAKE_A_PICTURE);
    }

    @Override
    public void onCancelClicked() {
        closeFragment();
    }


}