package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dk.events.a6.android.CreateEventController;
import dk.events.a6.android.CreateEventViewModel;
import dk.events.a6.R;
import dk.events.a6.databinding.ActivityCreateBinding;
import dk.events.a6.fragments.ChooseImageDialogFragment;
import dk.eventslib.gateways.EventGatewayInMemory;
import dk.eventslib.usecases.createevent.CreateEventOutputPort;
import dk.eventslib.usecases.createevent.CreateEventInputPort;
import dk.eventslib.usecases.createevent.CreateEventUseCaseImpl;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.entities.ImageDetails;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener, CreateEventOutputPort, ChooseImageDialogFragment.DialogListener {

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
            vm.title = editTextTitle.getText().toString();
            vm.description = editTextDescription.getText().toString();
            vm.ownerFirstName = "Jon";
            vm.ownerLastName = "Travolta";

            useCase = new CreateEventUseCaseImpl();
            EventGateway gateway = new EventGatewayInMemory();
            //CreateEventOutputPort outputPort = new CreateEventOutputPortImpl();

            useCase.setEventGateway(gateway);
            useCase.setOutputPort(this);

            CreateEventController createEventController = new CreateEventController(useCase);


            createEventController.createEvent(vm);

            //simulate back pressed
            onBackPressed();

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
            /*
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);*/
        }

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        showMsg("resultCode="+ resultCode + ", reqCode=" + reqCode, CreateActivity.this);

            try {
                if (resultCode == RESULT_OK && reqCode ==  RESULT_LOAD_IMG){
                    handleEventPictureFromGallery(data);
                }else if(resultCode == RESULT_OK && reqCode ==  RESULT_TAKE_A_PICTURE){
                    handleEventPictureFromCamera(data);
                }else {
                    showMsg("You haven't picked Image", CreateActivity.this);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                showMsg("Something went wrong", CreateActivity.this);
            }finally {
               closeFragment();
            }
    }

    private void handleEventPictureFromCamera(Intent data) throws FileNotFoundException {
        final Bitmap selectedImage = (Bitmap) data.getExtras().get("data");


        //https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        int size = selectedImage.getRowBytes() * selectedImage.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        selectedImage.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();


        ImageDetails imageDetails = new ImageDetails();
        imageDetails.setHeight(selectedImage.getHeight());
        imageDetails.setWidth(selectedImage.getWidth());
        imageDetails.setConfigName(selectedImage.getConfig().name());
        imageDetails.setPixels(byteArray);

        vm.createEventImages.add(imageDetails);

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


        //https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        int size = selectedImage.getRowBytes() * selectedImage.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        selectedImage.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();


        ImageDetails imageDetails = new ImageDetails();
        imageDetails.setHeight(selectedImage.getHeight());
        imageDetails.setWidth(selectedImage.getWidth());
        imageDetails.setConfigName(selectedImage.getConfig().name());
        imageDetails.setPixels(byteArray);

        vm.createEventImages.add(imageDetails);

        //
        //Bitmap.Config configBmp = Bitmap.Config.valueOf(bitmap.getConfig().name());
        //Bitmap bitmap_tmp = Bitmap.createBitmap(width, height, configBmp);
        //ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        //bitmap_tmp.copyPixelsFromBuffer(buffer);

        imageViewEventImage.setImageBitmap(selectedImage);
    }

    private void closeFragment() {
        getSupportFragmentManager().popBackStack();
    }

    private void showMsg(String msg, CreateActivity context) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    private ActivityCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.eventDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(binding.line);
            }
        });

        vm = new CreateEventViewModel();

        initialize();

        buttonCreateEvent.setOnClickListener(this);
        buttonAddImageCreate.setOnClickListener(this);
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

                new TimePickerDialog(CreateActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(CreateActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void initialize() {
        buttonCreateEvent = findViewById(R.id.buttonCreateEvent);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddImageCreate = findViewById(R.id.buttonAddImageCreate);
        imageViewEventImage = findViewById(R.id.imageViewEventImage);

    }

    @Override
    public void onChooseImageFromGalleryClicked() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onTakeAPictureClicked() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, RESULT_TAKE_A_PICTURE);
    }

    @Override
    public void onCancelClicked() {
        closeFragment();
    }
}