# antigravity-workspace-superpower
This Repo contains the code that are created using Antigravity Superpower extension i.e. https://www.npmjs.com/package/antigravity-superpowers

Since the repository `shdhumale/antigravity-workspace-superpower` is part of a growing ecosystem focused on the **Antigravity IDE** and **Agentic Workflows** (popularized by Siddharatha Dhumale's recent work on WebMCP and AI-first development), here is a professional `README.md` tailored for this project.

This README assumes the project is a "Superpower" (a collection of high-level agent instructions/skills) designed to be injected into an Antigravity workspace to enhance autonomous coding capabilities.

-----

# Antigravity Workspace Superpower 🚀

[](https://github.com/shdhumale)
[](https://opensource.org/licenses/MIT)

**Antigravity Workspace Superpower** is a curated collection of agentic instructions, patterns, and skills designed for the **Antigravity IDE**. It transforms your AI agent from a simple code completion tool into a high-functioning autonomous engineer capable of structured planning, test-driven development, and cross-module reasoning.

## 🌟 Overview

In the Antigravity ecosystem, a "Superpower" is more than just a prompt; it is a framework that governs how agents behave within a workspace. This repository provides the configuration files and `SKILL.md` templates necessary to implement a professional software development lifecycle (SDLC) directly within your agent's context.

### Key Capabilities:

  - **Structured Planning:** Forces agents to generate an `IMPLEMENTATION_PLAN.md` before touching code.
  - **TDD Enforcement:** Configures agents to follow the Red-Green-Refactor cycle.
  - **Context Management:** Optimized for `ag-refresh` to ensure agents maintain a deep understanding of your codebase.
  - **Multi-Agent Orchestration:** Templates for routing tasks between specialized agents (e.g., Architect, Coder, Tester).

## 🚀 Getting Started

### Prerequisites

  - [Antigravity IDE](https://www.google.com/search?q=https://github.com/google/antigravity) installed.
  - [Antigravity CLI (`ag`)](https://www.google.com/search?q=%5Bhttps://github.com/shdhumale%5D\(https://github.com/shdhumale\)) (recommended for workspace initialization).

### Installation

1.  **Clone this repository into your workspace:**

    ```bash
    git clone https://github.com/shdhumale/antigravity-workspace-superpower.git .antigravity/superpowers
    ```

2.  **Initialize the Superpower:**
    If you are using the Antigravity CLI, you can link these skills to your `.cursorrules` or `CLAUDE.md`:

    ```bash
    ag init --source .antigravity/superpowers
    ```

3.  **Refresh the Agent Context:**

    ```bash
    ag-refresh --workspace .
    ```

## 🛠 Included Skills & Workflows

This superpower injects several core "Skills" into your agent's memory:

| Skill | Description | Trigger |
| :--- | :--- | :--- |
| **`brainstorming`** | Refines rough ideas into technical specs. | `/brainstorm` |
| **`plan-first`** | Prevents code generation until a plan is approved. | Automated |
| **`test-driven`** | Requires a failing test before implementation. | `/code` |
| **`mcp-bridge`** | Connects your workspace to external MCP servers (e.g., MongoDB, Git). | Automated |

## 📂 Project Structure

```text
.
├── skills/                 # Individual SKILL.md files for agent behaviors
├── templates/              # Standardized templates for PRs, Plans, and Tests
├── .antigravity/           # Configuration for the Antigravity IDE
│   ├── rules.md            # Global behavior rules
│   └── conventions.md      # Project-specific coding standards
└── README.md
```

## 🧠 Philosophy

This project follows the **Agentic Web** philosophy:

1.  **Verification over Trust:** Agents must prove their work through tests and screenshots.
2.  **Context is King:** Small, hyper-relevant context windows are better than massive, noisy ones.
3.  **Traceability:** Every decision made by an agent should be logged in the workspace.

## 🤝 Contributing

Contributions are welcome\! If you have a specific workflow or skill that improves agent autonomy:

1.  Fork the repo.
2.  Create your skill in the `skills/` directory.
3.  Submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](https://www.google.com/search?q=LICENSE) file for details.

-----

*Created by [Siddharatha Dhumale](https://shdhumale.wordpress.com/) - Exploring the impact of AI on the future of software engineering.*
