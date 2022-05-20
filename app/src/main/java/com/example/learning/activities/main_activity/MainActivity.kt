package com.example.farmartpro.activities.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.learning.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Programmatically set the nav graph

        val myNavHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.mainactivityfragmentcontainer) as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.root_navigation)
        myNavHostFragment.navController.graph = graph


    }

}