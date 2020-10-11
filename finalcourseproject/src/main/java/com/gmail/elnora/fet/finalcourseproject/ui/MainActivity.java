package com.gmail.elnora.fet.finalcourseproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecipeListeners {

    private BottomNavigationView viewBottomNavigation;
    private TodoRecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new TodoRecipeViewModel(this);
        showMainFragmentAllRecipes(savedInstanceState);
        initBottomNavigation();
        bottomNavigationClickListeners();
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
            case R.id.menuItemExit : finish(); break;
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        setTitleToolbar(getResources().getString(R.string.app_name));
        return super.onOptionsItemSelected(item);
    }

    private void initBottomNavigation() {
        viewBottomNavigation = findViewById(R.id.viewBottomNavigation);
        viewBottomNavigation.setSelectedItemId(R.id.pageAllRecipes);
    }

    private void bottomNavigationClickListeners() {
        viewBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.pageSearchRecipes: {
                    showFragment(SearchRecipesFragment.getInstance(), SearchRecipesFragment.TAG);
                    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                    setTitleToolbar(getResources().getString(R.string.app_name));
                    return true;
                }
                case R.id.pageAllRecipes: {
                    showFragment(AllCategoriesRecipesFragment.getInstance(), AllCategoriesRecipesFragment.TAG);
                    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                    setTitleToolbar(getResources().getString(R.string.app_name));
                    return true;
                }
                case R.id.pageToDoRecipes: {
                    showFragment(TodoFragment.getInstance(), TodoFragment.TAG);
                    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                    setTitleToolbar(getResources().getString(R.string.app_name));
                    return true;
                }
                default: return false;
            }
        });
    }

    private void setTitleToolbar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
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
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                if ( stackHeight > 1) {
                    setTitleToolbar(getResources().getString(R.string.app_name));
                }
            } else {
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
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
        Snackbar.make(viewBottomNavigation, getString(R.string.snack_bar_text), Snackbar.LENGTH_LONG)
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
