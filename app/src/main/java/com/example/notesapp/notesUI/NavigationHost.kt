package com.example.notesapp.notesUI

import androidx.fragment.app.Fragment

interface NavigationHost {
    fun navigateTO(fragment: Fragment,addToBackStack: Boolean)
}