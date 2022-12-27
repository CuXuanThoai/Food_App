package com.example.foody.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [RecipesEntity::class],
    version = 1,
    exportSchema = true
)
    @TypeConverters(RecipesTypeConvert::class)
    abstract class RecipesDatabase :RoomDatabase() {

    abstract fun recipesdao (): RecipesDAO

}