package org.pinczow.images.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.pinczow.images.data.db.dao.ImageDao
import org.pinczow.images.data.db.dao.RemoteKeysDao
import org.pinczow.images.data.db.entity.ImageEntity
import org.pinczow.images.data.db.entity.RemoteKeys

@Database(
    entities = [
        ImageEntity::class,
        RemoteKeys::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun remoteKeysDao(): RemoteKeysDao


    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                ALTER TABLE image ADD favorite INTEGER DEFAULT 0
            """.trimIndent())
            }
        }
    }

}