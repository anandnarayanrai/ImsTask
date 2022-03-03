package com.ims.imstask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ims.imstask.data.AbstractExpandableDataProvider
import com.ims.imstask.ui.main.ExampleExpandableDataProviderFragment
import com.ims.imstask.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val FRAGMENT_TAG_DATA_PROVIDER = "data provider"
    private val FRAGMENT_LIST_VIEW = "list view"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    ExampleExpandableDataProviderFragment(),
                    FRAGMENT_TAG_DATA_PROVIDER
                )
                .commit()
            /*supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()*/

            supportFragmentManager.beginTransaction()
                .add(
                    R.id.container,
                    MainFragment(),
                    FRAGMENT_LIST_VIEW
                )
                .commit()
        }
    }

    fun getDataProvider(): AbstractExpandableDataProvider? {
        val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER)
        return (fragment as ExampleExpandableDataProviderFragment?)?.dataProvider
    }
}