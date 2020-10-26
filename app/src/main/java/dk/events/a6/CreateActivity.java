package dk.events.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dk.events.a6.gateways.EventGatewayInMemory;
import dk.events.a6.usecases.CreateEventOutputPort;
import dk.events.a6.usecases.CreateEventInputPort;
import dk.events.a6.usecases.CreateEventUseCase;
import dk.events.a6.usecases.EventGateway;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener, CreateEventOutputPort {

    private Button buttonCreateEvent;
    private EditText editTextTitle;
    private CreateEventViewModel vm;
    private CreateEventUseCase useCase;
    private EditText editTextDescription;


    @Override
    public void show(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        vm.title = editTextTitle.getText().toString();
        vm.description = editTextDescription.getText().toString();

        useCase = new CreateEventUseCase();
        EventGateway gateway = new EventGatewayInMemory();
        //CreateEventOutputPort outputPort = new CreateEventOutputPortImpl();

        useCase.setEventGateway(gateway);
        useCase.setOutputPort(this);

        CreateEventInputPort inputPort = new CreateEventInputPortImpl(useCase);


        inputPort.createEvent(vm.title, vm.description);

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
        buttonCreateEvent = findViewById(R.id.buttonCreateEvent);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
    }


}