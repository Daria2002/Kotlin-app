package com.example.work4experience.helper

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment

fun FragmentActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(id, fragment).commit()
}

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? =
    supportFragmentManager.findFragmentByTag(tag)

fun FragmentActivity.findFragmentById(tag: Int): Fragment? =
    supportFragmentManager.findFragmentById(tag);