package dk.events.a6.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dk.events.a6.R;
import dk.events.a6.activities.CreateActivity;

//https://www.journaldev.com/23096/android-dialogfragment

public class ChooseImageDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button buttonChooseImageFromGallery;
    private Button buttonTakeAPicture;
    private Button buttonAddPictureFrom;
    private Button buttonUseOurGallery;
    private Button buttonCancel;

    /*
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        if (getArguments() != null) {
            if (getArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert Dialog");
        builder.setMessage("Alert Dialog inside DialogFragment");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();

    }
    */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_image_dialog, container, false);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonChooseImageFromGallery.getId()){
            ((DialogListener)getActivity()).onChooseImageFromGalleryClicked();
        }else if(v.getId() == buttonTakeAPicture.getId()){
            ((DialogListener)getActivity()).onTakeAPictureClicked();
        }else if(v.getId() == buttonCancel.getId()){
            ((DialogListener)getActivity()).onCancelClicked();
        }
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonChooseImageFromGallery = view.findViewById(R.id.buttonChooseImageFromGallery);
        buttonTakeAPicture = view.findViewById(R.id.buttonTakeAPicture);
        buttonAddPictureFrom = view.findViewById(R.id.buttonAddPictureFrom);
        buttonUseOurGallery = view.findViewById(R.id.buttonUseOurGallery);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonChooseImageFromGallery.setOnClickListener(this);
        buttonTakeAPicture.setOnClickListener(this);
        buttonAddPictureFrom.setOnClickListener(this);
        buttonUseOurGallery.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        /*
        final EditText editText = view.findViewById(R.id.inEmail);
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("email")))
            editText.setText(getArguments().getString("email"));

        Button btnDone = view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogListener dialogListener = (DialogListener) getActivity();
                dialogListener.onFinishEditDialog(editText.getText().toString());
                dismiss();
            }
        });
        */
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean setFullScreen = false;
        if (getArguments() != null) {
            setFullScreen = getArguments().getBoolean("fullScreen");
        }

        if (setFullScreen)
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public interface DialogListener {
        void onChooseImageFromGalleryClicked();
        void onTakeAPictureClicked();
        void onCancelClicked();

    }



}
