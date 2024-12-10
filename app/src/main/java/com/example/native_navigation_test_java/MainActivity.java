package com.example.native_navigation_test_java;

import android.os.Bundle;
import android.widget.Toast;

import com.example.native_navigation_test_java.databinding.ActivityMainBinding;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static androidx.navigation.fragment.FragmentKt.findNavController;

public class MainActivity extends AppCompatActivity {

	ActivityMainBinding binding;
	AppBarConfiguration appBarConfiguration;
	private long backPress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setSupportActionBar(binding.toolbar);
		setContentView(binding.getRoot());
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		NavController navController = findNavController(binding.fragmentContainerView.getFragment());
		appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment).setOpenableLayout(binding.main).build();
		NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
		NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.navigationView, navController);
		navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> System.out.println(navDestination));


		OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {

			@Override
			public void handleOnBackPressed() {
				if (backPress + 2000 > System.currentTimeMillis()) {
					finish();
				} else {
					Toast.makeText(MainActivity.this, "Tap back button again in order to exit", Toast.LENGTH_SHORT).show();
				}

				backPress = System.currentTimeMillis();
			}
		};

		getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
	}
}