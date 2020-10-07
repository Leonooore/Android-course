package com.gmail.elnora.fet.finalcourseproject.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.gmail.elnora.fet.finalcourseproject.OnDishTypeClickListener;
import com.gmail.elnora.fet.finalcourseproject.OnRecipeClickListener;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.AllCategoriesRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.MyRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.RecipesListDishByCategoryFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.SearchRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.ToDoFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.ViewRecipeFragment;

public class MainActivity extends AppCompatActivity implements OnDishTypeClickListener, OnRecipeClickListener {

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
            case R.id.menuItemSearchRecipes : showFragment(SearchRecipesFragment.getInstance(), SearchRecipesFragment.TAG); break;
            case R.id.menuItemAllRecipes : showFragment(AllCategoriesRecipesFragment.getInstance(), AllCategoriesRecipesFragment.TAG); break;
            case R.id.menuItemMyRecipes : showFragment(MyRecipesFragment.getInstance(), MyRecipesFragment.TAG); break;
            case R.id.menuItemToDo : showFragment(ToDoFragment.getInstance(), ToDoFragment.TAG); break;
//            case R.id.menuItemSettings : //if there will be any settings
            case R.id.menuItemExit : finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buttonsClickListeners() {
        viewImageButtonSearch.setOnClickListener(view -> showFragment(SearchRecipesFragment.getInstance(), SearchRecipesFragment.TAG));
        viewButtonAllRecipes.setOnClickListener(view -> showFragment(AllCategoriesRecipesFragment.getInstance(), AllCategoriesRecipesFragment.TAG));
        viewButtonMyRecipes.setOnClickListener(view -> showFragment(MyRecipesFragment.getInstance(), MyRecipesFragment.TAG));
        viewButtonToDo.setOnClickListener(view -> showFragment(ToDoFragment.getInstance(), ToDoFragment.TAG));
    }

    private void showMainFragmentAllRecipes(Bundle savedInstanceState) {
        if (findViewById(R.id.fragmentContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            showFragment(AllCategoriesRecipesFragment.getInstance(), AllCategoriesRecipesFragment.TAG);
        }
    }

    private void showFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();
    }

    private void showFragmentBackStack(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDishTypeClick(DishTypeEnum dishType) {
        showFragmentBackStack(RecipesListDishByCategoryFragment.getInstance(dishType), RecipesListDishByCategoryFragment.TAG);
    }

    @Override
    public void onRecipeClick(RecipeDataModel recipeDataModel) {
        showFragmentBackStack(ViewRecipeFragment.getInstance(recipeDataModel), ViewRecipeFragment.TAG);
    }

}
