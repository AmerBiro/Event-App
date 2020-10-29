package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import dk.events.a6.CreateEventInputPortImpl;
import dk.events.a6.CreateEventViewModel;
import dk.events.a6.R;
import dk.events.a6.fragments.ChooseImageDialogFragment;
import dk.events.a6.gateways.EventGatewayInMemory;
import dk.events.a6.usecases.CreateEventOutputPort;
import dk.events.a6.usecases.CreateEventInputPort;
import dk.events.a6.usecases.CreateEventUseCase;
import dk.events.a6.usecases.EventGateway;
import dk.events.a6.usecases.entities.ImageDetails;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener, CreateEventOutputPort, ChooseImageDialogFragment.DialogListener {

    private Button buttonCreateEvent;
    private EditText editTextTitle;
    private CreateEventViewModel vm;
    private CreateEventUseCase useCase;
    private EditText editTextDescription;
    private Button buttonAddImageCreate;
    public static final int RESULT_LOAD_IMG = 0;
    public static final int RESULT_TAKE_A_PICTURE = 1;
    private ImageView imageViewEventImage;
    private FragmentTransaction fragmentTransaction;
    private ChooseImageDialogFragment dialogFragment;


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

            useCase = new CreateEventUseCase();
            EventGateway gateway = new EventGatewayInMemory();
            //CreateEventOutputPort outputPort = new CreateEventOutputPortImpl();

            useCase.setEventGateway(gateway);
            useCase.setOutputPort(this);

            CreateEventInputPort inputPort = new CreateEventInputPortImpl(useCase);


            inputPort.createEvent(vm);

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
                    handleEventPicture(data);
                }else if(resultCode == RESULT_OK && reqCode ==  RESULT_TAKE_A_PICTURE){
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageViewEventImage.setImageBitmap(photo);
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

    private void handleEventPicture(Intent data) throws FileNotFoundException {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        vm = new CreateEventViewModel();

        initialize();

        buttonCreateEvent.setOnClickListener(this);
        buttonAddImageCreate.setOnClickListener(this);
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