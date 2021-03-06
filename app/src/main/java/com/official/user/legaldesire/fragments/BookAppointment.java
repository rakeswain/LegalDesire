package com.official.user.legaldesire.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.official.user.legaldesire.R;
import com.official.user.legaldesire.models.LawyerData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by USER on 25-12-2018.
 */

public class BookAppointment extends AppCompatDialogFragment {
    EditText problem;
    Button send;
    ImageButton close ;
    FirebaseAuth mAuth;
    public static LawyerData lawyerData;
    SharedPreferences pref;
    int Exist=0;


    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.fragment_book_appointment,null);
        builder.setView(view);
        mAuth = FirebaseAuth.getInstance();
        problem=view.findViewById(R.id.entProblem);
        send = view.findViewById(R.id.sendBtn);

        close=view.findViewById(R.id.closebtn);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clickedclose", "onClick: " );
                dismiss();

                //return;
            }
        });
    //PROBLEM HERE
           send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(mAuth.getCurrentUser()!=null){
             final  String mail=lawyerData.getEmail().toString().replace('.',',');
              final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getEmail().replace(".",","));
               databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       if(dataSnapshot.hasChild("appointments"))
                       {
                            if(dataSnapshot.child("appointments").hasChild(mail)){
                                Toast.makeText(getContext(), "Request already sent!", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }else{
                                Toast.makeText(getContext(), "Request sent!", Toast.LENGTH_SHORT).show();
                                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Lawyers").child(mail);
                                databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("message").setValue(problem.getText().toString());
                                databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("status").setValue("-1");
                                databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("email").setValue(mAuth.getCurrentUser().getEmail());
                                databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("number").setValue("123456789");
                                databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("name").setValue("sonali");



                                //New Book


                                databaseReference.child("appointments").child(mail).child("message").setValue(problem.getText().toString());
                                databaseReference.child("appointments").child(mail).child("status").setValue("-1");
                                databaseReference.child("appointments").child(mail).child("mail").setValue(mail.replace(",","."));
                                databaseReference.child("appointments").child(mail).child("name").setValue(lawyerData.getName().toString());
                                databaseReference.child("appointments").child(mail).child("number").setValue(lawyerData.getContact().toString());
                                databaseReference.child("appointments").child(mail).child("areaOfPractice").setValue(lawyerData.getAreaOfPractice());
                            }
                       }else{
                           Toast.makeText(getContext(), "Request  sent!", Toast.LENGTH_SHORT).show();
                           DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Lawyers").child(mail);
                           databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("message").setValue(problem.getText().toString());
                           databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("status").setValue("-1");
                           databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("email").setValue(mAuth.getCurrentUser().getEmail());
                           databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("number").setValue("123456789");
                           databaseReference1.child("pending_appointments").child(mAuth.getCurrentUser().getEmail().replace(".",",")).child("name").setValue("sonali");



                           //New Book


                           databaseReference.child("appointments").child(mail).child("message").setValue(problem.getText().toString());
                           databaseReference.child("appointments").child(mail).child("status").setValue("-1");
                           databaseReference.child("appointments").child(mail).child("mail").setValue(mail.replace(",","."));
                           databaseReference.child("appointments").child(mail).child("name").setValue(lawyerData.getName().toString());
                           databaseReference.child("appointments").child(mail).child("number").setValue(lawyerData.getContact().toString());
                           databaseReference.child("appointments").child(mail).child("areaOfPractice").setValue(lawyerData.getAreaOfPractice());

                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });






               }
           else {
                   Toast.makeText(getContext(), "no current user", Toast.LENGTH_SHORT).show();
               }
           }
       });
        return builder.create();

    }
}

