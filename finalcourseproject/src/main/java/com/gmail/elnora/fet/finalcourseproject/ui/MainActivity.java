package com.gmail.elnora.fet.finalcourseproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.AllCategoriesRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.CookDishFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.RecipesListDishByCategoryFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.SearchRecipesFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.TodoFragment;
import com.gmail.elnora.fet.finalcourseproject.ui.fragments.ViewRecipeFragment;
import com.gmail.elnora.fet.finalcourseproject.viewmodel.TodoRecipeViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements RecipeListeners {

    private ImageButton viewImageButtonSearch;
    private Button viewButtonAllRecipes;
    private Button viewButtonToDo;
    private TodoRecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new TodoRecipeViewModel(this);
        initViews();
        buttonsClickListeners();
        showMainFragmentAllRecipes(savedInstanceState);
    }

    private void initViews() {
        viewImageButtonSearch = findViewById(R.id.viewImageButtonSearchRecipes);
        viewButtonAllRecipes = findViewById(R.id.viewButtonAllRecipes);
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
            case android.R.id.home : {
                getSupportFragmentManager().popBackStack();
                return true;
            }
            case R.id.menuItemSearchRecipes : showFragment(SearchRecipesFragment.getInstance(), SearchRecipesFragment.TAG); break;
            case R.id.menuItemAllRecipes : showFragment(AllCategoriesRecipesFragment.getInstance(), AllCategoriesRecipesFragment.TAG); break;
            case R.id.menuItemToDo : showFragment(TodoFragment.getInstance(), TodoFragment.TAG); break;
//            case R.id.menuItemSettings : //if there will be any settings
            case R.id.menuItemExit : finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitleToolbar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    private void buttonsClickListeners() {
        viewImageButtonSearch.setOnClickListener(view -> showFragment(SearchRecipesFragment.getInstance(), SearchRecipesFragment.TAG));
        viewButtonAllRecipes.setOnClickListener(view -> showFragment(AllCategoriesRecipesFragment.getInstance(), AllCategoriesRecipesFragment.TAG));
        viewButtonToDo.setOnClickListener(view -> showFragment(TodoFragment.getInstance(), TodoFragment.TAG));
    }

    private void showMainFragmentAllRecipes(Bundle savedInstanceState) {
        if (findViewById(R.id.fragmentContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, AllCategoriesRecipesFragment.getInstance(), AllCategoriesRecipesFragment.TAG)
                    .commit();
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

        setGoBackListener();
    }

    private void setGoBackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
            if (stackHeight > 0) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                if ( stackHeight > 1) {
                    setTitleToolbar(getResources().getString(R.string.app_name));
                }
            } else {
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                setTitleToolbar(getResources().getString(R.string.app_name));
            }
        });
    }

    @Override
    public void onDishTypeClick(DishTypeEnum dishType) {
        showFragmentBackStack(RecipesListDishByCategoryFragment.getInstance(dishType), RecipesListDishByCategoryFragment.TAG);
        setTitleToolbar(dishType.toString());
    }

    @Override
    public void onRecipeClick(RecipeDataModel recipeDataModel) {
        showFragmentBackStack(ViewRecipeFragment.getInstance(recipeDataModel), ViewRecipeFragment.TAG);
    }

    @Override
    public void onFabTodoCookClick(int recipeId) {
        showFragmentBackStack(CookDishFragment.getInstance(recipeId), CookDishFragment.TAG);
    }

    @Override
    public void onFabAddTodoListClick(TodoRecipeEntity recipe) {
        viewModel.insert(recipe);
        Snackbar.make(viewButtonToDo, getString(R.string.snack_bar_text), Snackbar.LENGTH_LONG)
                .setAction("Show", view -> showFragmentBackStack(TodoFragment.getInstance(), TodoFragment.TAG))
                .show();
    }

    @Override
    public void onTodoItemClick(int recipeId) {
        showFragmentBackStack(CookDishFragment.getInstance(recipeId), CookDishFragment.TAG);
        setTitleToolbar(getString(R.string.toolbar_text_cook_dish));
    }

    @Override
    public void onRemoveRecipeClick(TodoRecipeEntity recipe) {
        viewModel.delete(recipe);
    }

}
