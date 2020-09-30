package com.gmail.elnora.fet.finalcourseproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.AllCategoriesRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.RecipesListDishCategoryFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.MyRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.SearchRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.ToDoFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.ViewRecipeFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton viewImageButtonSearch;
    private Button viewButtonAllRecipes;
    private Button viewButtonMyRecipes;
    private Button viewButtonToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        buttonsClickListeners();
        showMainFragmentAllRecipes(savedInstanceState);
    }

    private void initViews() {
        viewImageButtonSearch = findViewById(R.id.viewImageButtonSearchRecipes);
        viewButtonAllRecipes = findViewById(R.id.viewButtonAllRecipes);
        viewButtonMyRecipes = findViewById(R.id.viewButtonMyRecipes);
        viewButtonToDo = findViewById(R.id.viewButtonToDoRecipes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuItemSearchRecipes : showSearchRecipesFragment(); break;
            case R.id.menuItemAllRecipes : showAllRecipesFragment(); break;
            case R.id.menuItemMyRecipes : showMyRecipesFragment(); break;
            case R.id.menuItemToDo : showToDoFragment(); break;
//            case R.id.menuItemSettings : //if there will be any settings
            case R.id.menuItemExit : finishActivity(0); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buttonsClickListeners() {
        viewImageButtonSearch.setOnClickListener(view -> showSearchRecipesFragment());
        viewButtonAllRecipes.setOnClickListener(view -> showAllRecipesFragment());
        viewButtonMyRecipes.setOnClickListener(view -> showMyRecipesFragment());
        viewButtonToDo.setOnClickListener(view -> showToDoFragment());
    }

    private void showMainFragmentAllRecipes(Bundle savedInstanceState) {
        if (findViewById(R.id.fragmentContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            showAllRecipesFragment();
        }
    }

    private void showAllRecipesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new AllCategoriesRecipesFragment(), AllCategoriesRecipesFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void showSearchRecipesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new SearchRecipesFragment(), SearchRecipesFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void showMyRecipesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new MyRecipesFragment(), MyRecipesFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void showToDoFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new ToDoFragment(), ToDoFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void showRecipesListDishCategory() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new RecipesListDishCategoryFragment(), RecipesListDishCategoryFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void showViewRecipeFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new ViewRecipeFragment(), ViewRecipeFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

}
