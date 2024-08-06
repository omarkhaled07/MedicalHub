package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.R
import com.google.android.material.navigation.NavigationView

class HomePatientActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    lateinit var PatientHistory: Button
    lateinit var NewCheck: Button
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var docName: TextView

    private lateinit var preference: SharedPrefManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_patient)

        preference = SharedPrefManager(this)
        PatientHistory = findViewById(R.id.PatientHistory)
        NewCheck = findViewById(R.id.NewCheck)

        NewCheck.setOnClickListener {
            startActivity(Intent(this, PatNewReservation::class.java))
        }

        PatientHistory.setOnClickListener {
            startActivity(Intent(this, AllPatientHistory::class.java))
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navView, navController)

        // Access the navigation header
        val headerView = navView.getHeaderView(0)
        val navHeaderTitle = headerView.findViewById<TextView>(R.id.nav_header_title)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_edit_profile -> {
                    navController.navigate(R.id.editProfilePatient)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_sign_out -> {
                    // Handle sign out
                    true
                }

                else -> false
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}