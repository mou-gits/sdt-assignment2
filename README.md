# Workflow Automation System — Assignment 2

This project implements a workflow automation engine using four core object‑oriented design patterns:

- **Factory Pattern** — constructing workflow steps from configuration  
- **Visitor Pattern** — adding operations without modifying step classes  
- **Iterator Pattern** — traversing workflow structures  
- **Memento Pattern** — supporting undo/redo in a workflow editor  

The system supports multiple step types, nested composite workflows, validation, cost estimation, pretty‑printing, and full undo/redo frameworks.

---

## 1. Overview

A workflow is represented as a tree of `Step` objects. Each step performs an action such as:

- Transforming data  
- Filtering data  
- Delaying execution  
- Sending notifications  
- Grouping steps inside a concept referred to as `CompositeStep`

The system reads configuration maps (simulating JSON), constructs the workflow, and allows external operations through visitors and iterators.

---

## 2. Design Decisions

### 2.1 Factory Pattern

The `StepFactory` class constructs concrete step objects based on a `"type"` field in the configuration map.

Key decisions:

- Implemented using a `switch` on lowercase type strings  
- Supports all required step types: `delay`, `notify`, `transform`, `filter`, `composite`  
- Composite creation is **recursive**, enabling nested workflows  
- Throws `IllegalArgumentException` for unknown or missing types  
- Ensures clean separation between configuration parsing and object creation  

---

### 2.2 Visitor Pattern

The Visitor Pattern allows adding new operations without modifying step classes.

Implemented visitors:

- **ValidationVisitor**  
  - Ensures required fields are present  
  - Ensures names are non‑empty  
  - Ensures delay values are positive  
  - Traverses nested composites and accumulates errors  

- **CostVisitor**  
  - Computes deterministic cost:  
    - Transform = 1  
    - Filter = 1  
    - Notify = 2  
    - Delay = ceil(ms / 100)  
  - Composite cost = sum of child costs  

- **PrettyPrintVisitor**  
  - Prints workflow structure with indentation  
  - Uses a depth counter  
  - Supports “shrinking cascade” indentation via `leaveComposite()`  
  - Stores output in a buffer for testing  

Design choices:

- `CompositeStep.accept()` handles traversal  
- Visitors remain stateless except for their accumulated results  
- Adding new visitors requires no changes to existing step classes  

---

### 2.3 Iterator Pattern

Two iterators were implemented:

- **DepthFirstIterator**  
  - Preorder DFS  
  - Uses a stack  
  - Pushes children in reverse order to preserve left‑to‑right traversal  
  - Includes composite nodes in traversal  

- **LinearIterator**  
  - Iterates only top‑level children of a composite  
  - If root is not composite, returns a single‑element iteration  

Design goals:

- Deterministic traversal  
- No recursion  
- Clean separation between traversal logic and step classes  

---

### 2.4 Memento Pattern

The `WorkflowEditor` supports:

- Adding steps  
- Removing steps  
- Editing step names  
- Undo  
- Redo  
- Clearing redo history after new edits  

Design decisions:

- Undo/redo stacks store **deep copies** of the workflow  
- Deep copy implemented manually for each step type  
- Composite steps are recursively cloned  
- Ensures past states are immutable and unaffected by future edits  

---

## 3. Example Workflow Configuration

Example configuration used in the demo:

```json
{
  "name": "LeadProcessing",
  "steps": [
    { "type": "transform", "name": "TrimName", "field": "name", "op": "trim" },
    { "type": "filter", "name": "OnlyGmail", "field": "email", "contains": "@gmail.com" },
    { "type": "delay", "name": "Delay500", "ms": 500 }
  ]
}
