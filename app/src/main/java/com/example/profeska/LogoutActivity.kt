package com.example.profeska

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.profeska.databinding.ActivityLogoutBinding

import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

class LogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutBinding
    private lateinit var user: FirebaseAuth
    lateinit var backToast:Toast
    private var backPressedTime:Long = 0
    val homeFragment = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen(window)
        user = FirebaseAuth.getInstance()

        val profileFragment = Fragment1()
        val addFragment = AddFragment()
        val notificationFragment = NotificationFragment()
        val chatPickFragment =ChatPickFragment()

        makeCurrentFragment(homeFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_add -> makeCurrentFragment(addFragment)
                R.id.ic_noti ->makeCurrentFragment(notificationFragment)
                R.id.ic_messeges->makeCurrentFragment(chatPickFragment)
            }
            true
        }




      /*  binding.btnProfil.setOnClickListener {
            replaceFragment(Fragment1())
        }*/
    }
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer,fragment)
            commit()
        }

    private fun replaceFragment(fragment: Fragment) {

            val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {



        backToast =  Toast.makeText(this, "Wciśnij wstecz jeszcze raz, aby zamknąć aplikacje", Toast.LENGTH_LONG)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            finishAffinity()

            exitProcess(0)

        } else {
            binding.bottomNavigation.setSelectedItemId(R.id.ic_home);

            makeCurrentFragment(homeFragment)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()



    }

}




