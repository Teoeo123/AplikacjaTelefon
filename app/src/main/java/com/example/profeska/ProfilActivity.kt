package com.example.profeska

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.profeska.databinding.ActivityProfilBinding
import com.example.profeska.databinding.ActivityProfilEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding
    private lateinit var myRef: DatabaseReference
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        fullScreen(window)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()
        val id=user.uid
        readUData(id)
        binding.btnEditProfP.setOnClickListener {
            startActivity(Intent(this,ProfilEditActivity::class.java))
        }





    }

    private fun readUData(user:String?, ){

        val firebase = FirebaseDatabase.getInstance("https://profeska-ad23d-default-rtdb.europe-west1.firebasedatabase.app")
        myRef = firebase.getReference("users").child(user.toString())
        myRef.get().addOnSuccessListener {

            if(it.exists()){
                binding.txtImieP.text="Imie: "+it.child("name").value.toString()
                binding.txtNazwiP.text="Nazwisko: "+it.child("sname").value.toString()
                binding.txtMiastP.text="Telefon: "+it.child("city").value.toString()
                binding.txtNumP.text="Miasto: "+it.child("number").value.toString()
                binding.txtPlecP.text="Płeć: "+it.child("sex").value.toString()
            }
            else{
                Toast.makeText(this,"Nie uzupełniłes profilu",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ProfilEditActivity::class.java))
            }
        }
    }
}