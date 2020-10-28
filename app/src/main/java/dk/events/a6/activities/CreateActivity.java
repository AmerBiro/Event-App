package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import dk.events.a6.gateways.EventGatewayInMemory;
import dk.events.a6.usecases.CreateEventOutputPort;
import dk.events.a6.usecases.CreateEventInputPort;
import dk.events.a6.usecases.CreateEventUseCase;
import dk.events.a6.usecases.EventGateway;
import dk.events.a6.usecases.entities.ImageDetails;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener, CreateEventOutputPort {

    private Button buttonCreateEvent;
    private EditText editTextTitle;
    private CreateEventViewModel vm;
    private CreateEventUseCase useCase;
    private EditText editTextDescription;
    private Button buttonAddImageCreate;
    private int RESULT_LOAD_IMG = 0;
    private ImageView imageViewEventImage;


    @Override
    public void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }


    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(CreateActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(CreateActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        vm = new CreateEventViewModel();

        initialize();



        buttonCreateEvent.setOnClickListener(this);
    }

    private void initialize() {
        buttonCreateEvent = findViewById(R.id.buttonAddImageCreate);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddImageCreate = findViewById(R.id.buttonAddImageCreate);
        imageViewEventImage = findViewById(R.id.imageViewEventImage);

    }


}