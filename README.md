# Re-KeyboardGPT
*Last updated on 03/05/2026*

> ⚠️ **This is a modified version of [KeyboardGPT](https://github.com/Mino260806/KeyboardGPT) by [Mino260806](https://github.com/Mino260806).**
> All credits go to the original author. This fork adds new AI providers and commands.

An **LSPosed Module** that lets you integrate Generative AI like ChatGPT in your favorite keyboard.

- [x] Tested up to Android 16, may work in later versions.
- [x] Works with Rooted and Unrooted devices.
- [x] Works in all apps.
- [x] Works with all keyboards.

<details>  
  <summary>Demo Video : Normal Prompt</summary>  

https://github.com/user-attachments/assets/bc054498-7aa6-4834-bf54-41d5e2b785b6

</details>  

<details>  
  <summary>Demo Video : Custom Prompts</summary>  

https://github.com/user-attachments/assets/ae3a4aff-3744-4b16-a3eb-18da2b5c1f3c

</details>  

<details>  
  <summary>Demo Video : Text Formatting</summary>  

https://github.com/user-attachments/assets/27043163-9920-48e3-bb02-0f68803d0be7

</details>  

<details>  
  <summary>Demo Video : Custom Patterns</summary>  

https://github.com/user-attachments/assets/80e9abab-7a4b-404e-9777-2d22436ce5e2

</details>  

<details>  
  <summary>Demo Video : Web Search</summary>  

https://github.com/user-attachments/assets/4cbf9088-0fb2-45d1-9a89-c0c72c1aff6e

</details>  

<p align="center">  
  <img src="demo/icon_border.png" alt="Icon" style="border: 10px solid black;"/>  
</p>  

## What's New in This Fork 🆕

### New AI Provider — CodexAPI (FREE! 🎉)
- Added **CodexAPI** — a completely **free** AI provider, no API key required!
- Supports **35+ models** including GPT-5, Grok-4, DeepSeek, Gemini, Claude, Llama, Qwen, Mistral, and more
- Model selection via dropdown — no need to remember model IDs
- Based on: [Codexapi](https://chatbot.codexapi.workers.dev/docs)

### New Commands
- `%tr <text>%` — Translate any language text to English automatically
- `%?%` — Show all available commands in a popup dialog

---

## Tested Keyboards

- [Google Gboard](https://play.google.com/store/apps/details?id=com.google.android.inputmethod.latin)
- [Microsoft Swiftkey](https://play.google.com/store/apps/details?id=com.touchtype.swiftkey)
- [Yandex Keyboard](ru.yandex.androidkeyboard)
- [Simple Keyboard](https://play.google.com/store/apps/details?id=rkr.simplekeyboard.inputmethod)
- [Futo Keyboard](https://play.google.com/store/apps/details?id=org.futo.inputmethod.latin.playstore)

## Features

- AI chat completions (supports normal and custom prompts)
- Text Formatting (bold, italic, strikethrough, underline)
- Web Search
- **Translation to English** (New!)
- **Help command popup** (New!)
- **CodexAPI support with 35+ free models** (New!)

## Install Guide

#### Root
1. Install module APK from [releases](https://github.com/DominatorStufs/Re-KeyboardGPT/releases/) or build from [Actions](https://github.com/DominatorStufs/Re-KeyboardGPT/actions)
2. Enable module in LSPosed and select your favorite keyboard
3. Force close the keyboard from settings, or restart your phone

#### No Root
1. Install module APK
2. Patch your favorite keyboard APK in LSPatch Manager and follow the instructions

## Usage Guide

| Command | Description |
|---|---|
| `*#settings#*` | Open module settings |
| `$<prompt>$` | Send AI prompt |
| `$$` | Configure AI provider (model, API key...) |
| `%<prefix> <prompt>%` | Submit a custom prompt |
| `%%` | Configure custom prompts |
| `%s <text>%` | Web search |
| `%tr <text>%` | 🌐 Translate text to English **(New!)** |
| `%?%` | ❓ Show all commands popup **(New!)** |
| `\|<text>\|` | *Italic* text |
| `@<text>@` | **Bold** text |
| `~<text>~` | ~~Strikethrough~~ text |
| `_<text>_` | Underline text |

## Supported AI Providers

| Provider | Free? | API Key Required? |
|---|---|---|
| **CodexAPI** 🆕 | ✅ Yes | ❌ No |
| Gemini | ✅ Yes | ✅ Yes |
| Groq | ✅ Yes | ✅ Yes |
| OpenRouter | ✅ Yes | ✅ Yes |
| ChatGPT | ❌ No | ✅ Yes |
| Claude | ❌ No | ✅ Yes |
| Mistral | ❌ No | ✅ Yes |

### CodexAPI Available Models (35+)
`gpt-5`, `gpt-5.1`, `gpt-5.2`, `chatgpt-4o-latest`, `o1-preview`, `o3-mini`,
`anthropic/claude-sonnet-4`, `google/gemini-2.5-pro-preview-05-06`, `x-ai/grok-4`,
`deepseek-ai/deepseek-v3.2`, `deepseek-ai/deepseek-R1-0528`,
`meta/llama-4-maverick-17b-128e-instruct`, `meta-llama-3.3-70b-instruct`,
`qwen/qwen3-235b-a22b`, `qwen/qwq-32b`, `moonshotai/kimi-k2-thinking`,
`mistralai/mistral-large-3-675b-instruct-2512`, `mercury-coder`, and many more!

### Free API Keys (Other Providers)
- Google Gemini → [Get Key](https://aistudio.google.com/app/apikey)
- Groq → [Get Key](https://console.groq.com/keys)
- OpenRouter → [Get Key](https://openrouter.ai/settings/keys)

## Credits

- **Original project**: [KeyboardGPT](https://github.com/Mino260806/KeyboardGPT) by [Mino260806](https://github.com/Mino260806)
- **This fork**: [Re-KeyboardGPT](https://github.com/DominatorStufs/Re-KeyboardGPT) by [DominatorStufs](https://github.com/DominatorStufs)
- **CodexAPI**: Free AI API by [@nepcodexcc](https://t.me/nepcodexcc)

## Links
[Original XDA Thread](https://xdaforums.com/t/mod-xposed-integrate-generative-ai-like-chatgpt-in-keyboard.4683421/)

[Original Telegram](https://t.me/keyboard_gpt)
