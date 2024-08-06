package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainActivity
import com.example.medicalhub.R
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    lateinit var toggle:ActionBarDrawerToggle
    lateinit var docName : TextView
    lateinit var allReservation: Button
    lateinit var newCheck : Button
    lateinit var myWorkingTime : Button
    lateinit var DocHistory: Button
    private lateinit var preference: SharedPrefManager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        preference = SharedPrefManager(this)
        allReservation = findViewById(R.id.allReservation)
        newCheck = findViewById(R.id.NewCheck)
        docName = findViewById(R.id.docName)
        myWorkingTime = findViewById(R.id.myWorkingTime)
        DocHistory = findViewById(R.id.DocHistory)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navView, navController)

        DocHistory.setOnClickListener {
            startActivity(Intent(this, DoctorHistoryRosheta::class.java))
        }

        // Access the navigation header
        val headerView = navView.getHeaderView(0)
        val navHeaderTitle = headerView.findViewById<TextView>(R.id.nav_header_title)

        navHeaderTitle.text = preference.getPrefVal(this).getString("username", "")

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    navController.navigate(R.id.profileFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_sign_out -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Clear the back stack
                    startActivity(intent)
                    finish()  // Finish the current activity
                    true
                }
                else -> false
            }
        }

        docName.text = preference.getPrefVal(this).getString("username", "")

        newCheck.setOnClickListener {
            startActivity(Intent(this, DocNewCheck::class.java))
        }
        allReservation.setOnClickListener {
            startActivity(Intent(this, AllPatientsReservation::class.java))
        }

        myWorkingTime.setOnClickListener {
            startActivity(Intent(this, MyWorkingTime::class.java))
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