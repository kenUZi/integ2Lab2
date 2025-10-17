# 🎬 Modular Media Streaming Suite (Structural Design Patterns)

## 📖 Overview
This project refactors a **legacy media player** into a **Modular Media Streaming Suite** using **Structural Design Patterns** in Java.  
It demonstrates modularity, flexibility, and scalability by integrating multiple design patterns such as **Adapter**, **Bridge**, **Decorator**, **Composite**, and **Proxy**.

---

## 🧱 Implemented Design Patterns

| Pattern | Purpose | Example in Code |
|----------|----------|----------------|
| **Adapter** | Integrates multiple media sources (Local, HLS, Remote API) | `MediaSource`, `LocalFilePlayer`, `HLSStreamPlayer`, `RemoteAPIPlayer` |
| **Bridge** | Allows switching between rendering modes (hardware/software) | `Renderer`, `HardwareRenderer`, `SoftwareRenderer` |
| **Decorator** | Dynamically adds features like subtitles, equalizer, watermark | `SubtitleDecorator`, `EqualizerDecorator`, `WatermarkDecorator` |
| **Composite** | Supports nested playlists and hierarchical structures | `Playlist`, `Song` |
| **Proxy** | Adds caching for remote streaming to improve efficiency | `RemoteMediaProxy`, `RealRemoteMedia` |

---

## 🏗️ Project Structure

