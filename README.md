# 📝 Notes App with Export Feature

A simple yet powerful Android Notes App built using **Kotlin**, featuring note creation, editing, deletion, and export functionality. The app uses **Room Database** for local data persistence and **RecyclerView** for efficiently displaying a list of notes. Users can also export individual notes as `.txt` files for external use.

## ✨ Features

- ➕ **Create** and save new notes
- 🖊️ **Update** existing notes
- ❌ **Delete** notes with confirmation
- 📋 **Display all notes** using RecyclerView
- 💾 **Persistent storage** using Room Database (SQLite)
- 📤 **Export individual notes to `.txt` format**
- 📱 Clean and intuitive UI with Material Design

## 📸 Screenshots

| All Notes | Add Note | Export Note |
|-----------|----------|-------------|
| ![list](assets/notes_list.png) | ![add](assets/add_note.png) | ![export](assets/export_note.png) |

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: XML, RecyclerView, Material Design Components
- **Database**: Room (Entity, DAO, Database)
- **Architecture**: MVVM (optional)
- **Export**: Android file I/O using `FileOutputStream`

