package com.umutemregithub.app.ui.home.search

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.umutemregithub.app.R
import com.umutemregithub.app.databinding.LanguageViewBinding

class LanguageView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding: LanguageViewBinding

    init {
        binding = LanguageViewBinding.inflate(LayoutInflater.from(context), this, true)
    }


    fun setProfileView(languageName: String?){
        val colorResource = CodingLanguage.getColorResource(languageName)
        val color = ContextCompat.getColor(context, colorResource)

        binding.coloredCircle.setColorFilter(color, PorterDuff.Mode.SRC_IN)

        if (languageName != null){
            binding.languageName.text = languageName
        } else {
            binding.languageName.text = context.getString(R.string.no_language_detected)
        }
    }
}

enum class CodingLanguage(private val languageName: String?, private val languageColor: Int) {
    JAVA("Java", R.color.java_color),
    KOTLIN("Kotlin", R.color.kotlin_color),
    PYTHON("Python", R.color.python_color),
    C("C", R.color.c_color),
    CPP("C++", R.color.cpp_color),
    SWIFT("Swift", R.color.swift_color),
    JAVASCRIPT("JavaScript", R.color.javascript_color),
    HTML("HTML", R.color.html_color),
    CSHARP("C#", R.color.c_sharp_color),
    MAKEFILE("Makefile", R.color.makefile_color),
    DART("Dart", R.color.dart_color),
    RUST("Rust", R.color.rust_color),
    R_LANGUAGE("R", R.color.r_color),
    CRYSTAL("Crystal", R.color.crystal_color),
    TYPESCRIPT("TypeScript", R.color.type_script_color),
    GO("Go", R.color.go_color);

    companion object {
        fun getColorResource(language: String?): Int {
            return values().find { it.languageName == language }?.languageColor ?: R.color.black
        }
    }
}
