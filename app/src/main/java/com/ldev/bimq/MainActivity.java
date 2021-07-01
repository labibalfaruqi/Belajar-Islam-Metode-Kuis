package com.ldev.bimq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CheckerMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TabLayout tabLayout = (TabLayout) this.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_icon_pilihan_ganda_active));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_icon_benar_salah_inactive));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_icon_materi_inactive));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_icon_profile_inactive));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) this.findViewById(R.id.pager);
        final TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new PilihanGandaFragment());
        tabAdapter.addFragment(new BenarSalahFragment());
        tabAdapter.addFragment(new MateriFragment());
        tabAdapter.addFragment(new ProfileFragment());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Intent intent = getIntent();
        String fragmentPilihan = intent.getStringExtra("fragmentPilihan");
        Log.d(TAG, "fragmentPilihan: " + fragmentPilihan);
        if (fragmentPilihan != null) {
            int pilihan = 0;
            switch (fragmentPilihan) {
                case "Pilihan Ganda":
                    pilihan = 0;
                    break;
                case "Benar Salah":
                    pilihan = 1;
                    break;
                case "Materi":
                    pilihan = 2;
                    break;
                case "Profile":
                    pilihan = 3;
                    break;
            }
            viewPager.setCurrentItem(pilihan);
            setIcon(pilihan, tabLayout);
            fragmentPilihan = null;
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int selectedTabPosition = tab.getPosition();
                setIcon(selectedTabPosition, tabLayout);
//                if (selectedTabPosition == 0) { // that means first tab
//                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_active);
//                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_inactive);
//                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_inactive);
//                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_inactive);
//                } else if (selectedTabPosition == 1) {
//                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_inactive);
//                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_active);
//                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_inactive);
//                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_inactive);
//                } else if (selectedTabPosition == 2) {
//                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_inactive);
//                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_inactive);
//                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_active);
//                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_inactive);
//                } else {
//                    tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_inactive);
//                    tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_inactive);
//                    tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_inactive);
//                    tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_active);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setIcon(int selectedTabPosition, TabLayout tabLayout) {
        switch (selectedTabPosition) {
            case 0:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_active);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_inactive);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_inactive);
                tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_inactive);
                break;
            case 1:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_inactive);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_active);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_inactive);
                tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_inactive);
                break;
            case 2:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_inactive);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_inactive);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_active);
                tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_inactive);
                break;
            case 3:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_pilihan_ganda_inactive);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_benar_salah_inactive);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_materi_inactive);
                tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_profile_active);
                break;
        }
    }
}