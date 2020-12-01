package com.example.instagram

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instagram.navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
//메인 엑티비티에서 하단 바텀네비게이션 구현부
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
    }
    //화면 전환 onNavigationItemSelected 사용
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
       when(p0.itemId){
           //홈 화면 전환부
           R.id.action_home->{
               var detailViewFragment = DetailViewFragment()
               supportFragmentManager.beginTransaction()
                   .replace(R.id.main_content, detailViewFragment)
                   .commit()
               return true
           }
           //검색 화면 전환부
           R.id.action_search->{
               var gridFragment = GridFragment()
               supportFragmentManager.beginTransaction()
                   .replace(R.id.main_content,gridFragment)
                   .commit()
               return true
           }
           //사진찍는 전환부
           R.id.action_add_photo->{//사진 경로를 가져올수있는 권한 요청
               if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){ //외부스토리지 권한을 가져올수 있는지 체크 하는 부
                   startActivity(Intent(this,AddPhotoActivity::class.java))
               }
               return true
           }
           //좋아요 전환
           R.id.action_favorite_alarm->{
               var alarmFragment = AlarmFragment()
               supportFragmentManager.beginTransaction()
                   .replace(R.id.main_content,alarmFragment)
                   .commit()
               return true
           }
           //계정 화면 전환
           R.id.action_account->{
               var userFragment = UserFragment()
               var bundle = Bundle()
               var uid = FirebaseAuth.getInstance().currentUser?.uid

               bundle.putString("destinationUid", uid)
               userFragment.arguments = bundle
               supportFragmentManager.beginTransaction()
                   .replace(R.id.main_content,userFragment)
                   .commit()
               return true
           }
       }
        return false
    }

}