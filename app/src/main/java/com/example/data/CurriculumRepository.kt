package com.example.data

object CurriculumRepository {

    val languages: List<Language> = listOf(
        Language(
            id = "python",
            name = "Python",
            tagline = "Readable, Dynamic & High-Level",
            iconSymbol = "🐍",
            brandColorHex = 0xFF10B981, // Emerald Teal
            lightBgColorHex = 0xFFECFDF5,
            description = "Interpreted, dynamically typed, multi-paradigm language emphasizing code readability with clean indentation.",
            paradigm = "Multi-paradigm (OOP, Functional, Imperative)",
            releaseYear = "1991",
            typingSystem = "Dynamic, Strong",
            memoryModel = "Automatic (Reference Counting + Cyclic GC)"
        ),
        Language(
            id = "java",
            name = "Java",
            tagline = "Enterprise, Robust & JVM-Powered",
            iconSymbol = "☕",
            brandColorHex = 0xFFF59E0B, // Warm Amber
            lightBgColorHex = 0xFFFFFBEB,
            description = "Class-based, object-oriented language designed with the 'Write Once, Run Anywhere' JVM architecture.",
            paradigm = "Object-Oriented, Structured",
            releaseYear = "1995",
            typingSystem = "Static, Strong",
            memoryModel = "Automatic (JVM Generational Garbage Collector)"
        ),
        Language(
            id = "c",
            name = "C",
            tagline = "Low-Level, Fast & Foundational",
            iconSymbol = "{C}",
            brandColorHex = 0xFF6366F1, // Indigo Violet
            lightBgColorHex = 0xFFEEF2FF,
            description = "Imperative procedural language providing close-to-hardware access and direct memory manipulation.",
            paradigm = "Procedural, Structured",
            releaseYear = "1972",
            typingSystem = "Static, Weak",
            memoryModel = "Manual (malloc, calloc, free)"
        ),
        Language(
            id = "cpp",
            name = "C++",
            tagline = "Zero-Cost Abstractions & High Performance",
            iconSymbol = "C++",
            brandColorHex = 0xFF818CF8, // Deep Violet / Indigo
            lightBgColorHex = 0xFFF5F3FF,
            description = "General-purpose systems programming language featuring classes, templates, and RAII resource safety.",
            paradigm = "Multi-paradigm (Procedural, OOP, Generic)",
            releaseYear = "1985",
            typingSystem = "Static, Strong",
            memoryModel = "Manual / RAII (Smart Pointers: unique_ptr, shared_ptr)"
        )
    )

    fun getLanguage(id: String): Language {
        return languages.find { it.id == id } ?: languages[0]
    }

    val concepts: List<Concept> = listOf(
        // PYTHON CONCEPTS
        Concept(
            id = "py_intro",
            languageId = "python",
            title = "Introduction & Hello World",
            category = "Fundamentals",
            difficulty = "Beginner",
            estimatedMinutes = 5,
            shortSummary = "Entry point of a Python program with simple print statements and no boilerplate.",
            detailedExplanation = "Python is an interpreted scripting language where code executes top-to-bottom without requiring explicit class wrappers or main method declarations.",
            coreTakeaways = listOf(
                "No class or main boilerplate required for simple scripts.",
                "print() handles newline appending automatically.",
                "Code blocks are demarcated by 4-space indentation, not curly braces."
            ),
            codeSnippet = "# Python 3: Hello World\ndef main():\n    greeting = \"Hello, CodeCompare!\"\n    print(greeting)\n    print(\"Version: 3.x | Clean indentation\")\n\nif __name__ == \"__main__\":\n    main()",
            expectedOutput = "Hello, CodeCompare!\nVersion: 3.x | Clean indentation",
            gotchas = listOf(
                "Tabs and spaces cannot be mixed for indentation.",
                "print in Python 3 is a function call print(), unlike Python 2."
            )
        ),
        Concept(
            id = "py_vars",
            languageId = "python",
            title = "Variables & Dynamic Typing",
            category = "Fundamentals",
            difficulty = "Beginner",
            estimatedMinutes = 8,
            shortSummary = "Variables do not require explicit type declarations and rebind dynamically.",
            detailedExplanation = "In Python, variables are names that refer to objects in memory. The type is tied to the value, not the variable name.",
            coreTakeaways = listOf(
                "Dynamic binding: x = 42 then x = 'text' is completely valid.",
                "Type hints exist for static analyzers but are not enforced at runtime.",
                "Immutable primitives: numbers, strings, and tuples cannot be altered in-place."
            ),
            codeSnippet = "score = 95          # int\npi = 3.14159        # float\nlanguage = \"Python\" # str\n\n# Dynamic reassignment\ncontainer = [1, 2, 3]\ncontainer = \"Now I am a string!\"\nprint(f\"Value: {container} | Type: {type(container).__name__}\")",
            expectedOutput = "Value: Now I am a string! | Type: str",
            gotchas = listOf(
                "Strong typing prevents implicit conversion like 'score: ' + 95 (raises TypeError).",
                "Be careful not to overwrite built-in keywords like list or str."
            )
        ),
        Concept(
            id = "py_loops",
            languageId = "python",
            title = "Loops & Iterations",
            category = "Control Flow",
            difficulty = "Beginner",
            estimatedMinutes = 8,
            shortSummary = "for-in iteration, range(), while loops, and list comprehensions.",
            detailedExplanation = "Python loops operate natively on iterables. Rather than counting indices manually, for item in collection: directly iterates over items.",
            coreTakeaways = listOf(
                "range(start, stop, step) creates a lazy generator for index loops.",
                "enumerate() provides both index and value seamlessly.",
                "List comprehensions provide a concise syntax to build transformed lists."
            ),
            codeSnippet = "fruits = [\"apple\", \"banana\", \"cherry\"]\n\nfor idx, fruit in enumerate(fruits, start=1):\n    print(f\"{idx}. {fruit}\")\n\nsquared = [x**2 for x in range(1, 6) if x % 2 == 0]\nprint(\"Squared evens:\", squared)",
            expectedOutput = "1. apple\n2. banana\n3. cherry\nSquared evens: [4, 16]",
            gotchas = listOf(
                "Modifying a list while iterating over it causes unexpected skips.",
                "Python loops have an optional 'else:' block that runs if no break occurred."
            )
        ),
        Concept(
            id = "py_classes",
            languageId = "python",
            title = "Classes & OOP",
            category = "OOP & Memory",
            difficulty = "Intermediate",
            estimatedMinutes = 12,
            shortSummary = "class definitions, __init__ constructor, self binding, and inheritance.",
            detailedExplanation = "Python is deeply object-oriented. Everything is an object, including numbers and functions. Methods receive the current instance explicitly as self.",
            coreTakeaways = listOf(
                "__init__ is the instance constructor.",
                "self must be declared as the first parameter of every instance method.",
                "Supports multiple inheritance via C3 linearization (MRO)."
            ),
            codeSnippet = "class Developer:\n    def __init__(self, name: str, lang: str):\n        self.name = name\n        self.lang = lang\n\n    def describe(self) -> str:\n        return f\"{self.name} writes {self.lang}\"\n\ndev = Developer(\"Elena\", \"Python\")\nprint(dev.describe())",
            expectedOutput = "Elena writes Python",
            gotchas = listOf(
                "No private keyword; _var is convention, __var invokes name mangling.",
                "Forgetting self in method signature throws TypeError on invocation."
            )
        ),

        // JAVA CONCEPTS
        Concept(
            id = "java_intro",
            languageId = "java",
            title = "Introduction & Hello World",
            category = "Fundamentals",
            difficulty = "Beginner",
            estimatedMinutes = 6,
            shortSummary = "Class structure, public static void main, and System.out.println.",
            detailedExplanation = "Every Java program must reside inside a class definition. The JVM starts execution at the public static void main method.",
            coreTakeaways = listOf(
                "Source file name must strictly match the public class name (Main.java).",
                "Strict static typing requires every variable and method to declare types.",
                "Compiles to bytecode (.class) which runs on the Java Virtual Machine."
            ),
            codeSnippet = "public class Main {\n    public static void main(String[] args) {\n        String greeting = \"Hello, CodeCompare from Java!\";\n        System.out.println(greeting);\n        System.out.println(\"Type-safe, JVM compiled.\");\n    }\n}",
            expectedOutput = "Hello, CodeCompare from Java!\nType-safe, JVM compiled.",
            gotchas = listOf(
                "Forgetting 'static' on main prevents JVM execution.",
                "Semicolons are mandatory at the end of every statement."
            )
        ),
        Concept(
            id = "java_vars",
            languageId = "java",
            title = "Variables & Strong Static Typing",
            category = "Fundamentals",
            difficulty = "Beginner",
            estimatedMinutes = 8,
            shortSummary = "Primitive types vs Object wrappers, and local variable type inference (var).",
            detailedExplanation = "Java separates 8 primitive types stored on the stack from reference objects allocated on the heap.",
            coreTakeaways = listOf(
                "Primitives have fixed bit widths across all architectures (e.g. int is 32-bit).",
                "Java 10+ supports 'var' for local variables with compile-time inferred type.",
                "Autoboxing converts primitives to their wrapper objects automatically."
            ),
            codeSnippet = "public class DataTypesDemo {\n    public static void main(String[] args) {\n        int score = 100;\n        double pi = 3.14159;\n        var message = \"Strongly typed Java\";\n\n        System.out.println(\"Score: \" + score + \", Message: \" + message);\n    }\n}",
            expectedOutput = "Score: 100, Message: Strongly typed Java",
            gotchas = listOf(
                "Watch out for NullPointerException when unboxing null wrappers.",
                "var cannot be used for class fields, only local variables."
            )
        ),
        Concept(
            id = "java_loops",
            languageId = "java",
            title = "Loops & Stream API",
            category = "Control Flow",
            difficulty = "Intermediate",
            estimatedMinutes = 9,
            shortSummary = "Standard for, enhanced for-each, while loops, and modern Stream API pipelines.",
            detailedExplanation = "Java provides classical C-style loops alongside modern functional Stream pipelines for mapping, filtering, and reducing collections.",
            coreTakeaways = listOf(
                "Enhanced for-each loop works over any object implementing Iterable.",
                "Stream API enables declarative data transformations.",
                "Loop conditions must evaluate to a strict boolean expression."
            ),
            codeSnippet = "import java.util.List;\n\npublic class LoopsDemo {\n    public static void main(String[] args) {\n        var langs = List.of(\"Java\", \"Python\", \"C++\");\n\n        for (String lang : langs) {\n            System.out.println(\"Lang: \" + lang);\n        }\n    }\n}",
            expectedOutput = "Lang: Java\nLang: Python\nLang: C++",
            gotchas = listOf(
                "List.of() is immutable; calling .add() throws UnsupportedOperationException.",
                "Java does not support truthy non-booleans in conditions."
            )
        ),
        Concept(
            id = "java_classes",
            languageId = "java",
            title = "Classes, Interfaces & Records",
            category = "OOP & Memory",
            difficulty = "Intermediate",
            estimatedMinutes = 12,
            shortSummary = "Encapsulation, access modifiers, and Java 14+ records for data carriers.",
            detailedExplanation = "Java strictly adheres to Object-Oriented paradigms. Classes encapsulate state and behavior. Java 14+ introduces 'record' for immutable data transfer carriers.",
            coreTakeaways = listOf(
                "Everything inherits from java.lang.Object.",
                "Single class inheritance, but multiple interface implementation.",
                "Records automatically generate getters, equals, hashCode, and toString."
            ),
            codeSnippet = "record Developer(String name, String language, int yearsExp) {}\n\npublic class OOPDemo {\n    public static void main(String[] args) {\n        var dev = new Developer(\"Marcus\", \"Java\", 6);\n        System.out.println(dev);\n    }\n}",
            expectedOutput = "Developer[name=Marcus, language=Java, yearsExp=6]",
            gotchas = listOf(
                "Do not use == to compare Strings or Objects; always use .equals().",
                "Package-private is the default visibility when no modifier is given."
            )
        ),

        // C CONCEPTS
        Concept(
            id = "c_intro",
            languageId = "c",
            title = "Introduction & Main Function",
            category = "Fundamentals",
            difficulty = "Beginner",
            estimatedMinutes = 6,
            shortSummary = "#include preprocessor directives, int main() entry point, and printf.",
            detailedExplanation = "C is a procedural compiled language. Code is converted directly into machine instructions. The preprocessor handles header inclusions before compilation.",
            coreTakeaways = listOf(
                "#include <stdio.h> provides standard input/output functions.",
                "int main(void) returns an exit status code (0 indicates success).",
                "printf requires explicit format specifiers (%d, %s, %f)."
            ),
            codeSnippet = "#include <stdio.h>\n\nint main(void) {\n    char greeting[] = \"Hello from C!\";\n    int year = 1972;\n\n    printf(\"%s\\n\", greeting);\n    printf(\"Created by Dennis Ritchie in %d\\n\", year);\n    return 0;\n}",
            expectedOutput = "Hello from C!\nCreated by Dennis Ritchie in 1972",
            gotchas = listOf(
                "Wrong format specifiers causes undefined behavior.",
                "Strings in C are null-terminated char arrays, not built-in string types."
            )
        ),
        Concept(
            id = "c_pointers",
            languageId = "c",
            title = "Pointers & Manual Memory (malloc/free)",
            category = "OOP & Memory",
            difficulty = "Advanced",
            estimatedMinutes = 15,
            shortSummary = "Memory addresses (&), dereferencing (*), dynamic heap allocation and deallocation.",
            detailedExplanation = "C gives direct control over computer memory. Pointers store hardware addresses. Heap memory allocated with malloc must be released with free.",
            coreTakeaways = listOf(
                "& operator obtains variable memory address; * dereferences pointer.",
                "malloc(size) allocates raw bytes on heap; returns void*.",
                "Every malloc MUST be paired with free() to prevent memory leaks."
            ),
            codeSnippet = "#include <stdio.h>\n#include <stdlib.h>\n\nint main(void) {\n    int *arr = (int*)malloc(3 * sizeof(int));\n    if (!arr) return 1;\n\n    for (int i = 0; i < 3; i++) arr[i] = (i + 1) * 10;\n    for (int i = 0; i < 3; i++) printf(\"Val: %d\\n\", arr[i]);\n\n    free(arr);\n    arr = NULL;\n    return 0;\n}",
            expectedOutput = "Val: 10\nVal: 20\nVal: 30",
            gotchas = listOf(
                "Dangling pointers: using memory after freeing it causes crashes.",
                "Buffer overflows: writing beyond allocated bounds corrupts the heap."
            )
        ),
        Concept(
            id = "c_loops",
            languageId = "c",
            title = "Loops & Control Flow",
            category = "Control Flow",
            difficulty = "Beginner",
            estimatedMinutes = 7,
            shortSummary = "for, while, do-while loops and switch statements.",
            detailedExplanation = "C provides minimal and fast control structures directly mirrored by CPU jump instructions.",
            coreTakeaways = listOf(
                "Standard 3-part for loop: for (init; condition; step).",
                "Non-zero integers are considered truthy; 0 is false.",
                "switch cases require 'break;' or fallthrough will occur."
            ),
            codeSnippet = "#include <stdio.h>\n\nint main(void) {\n    int total = 0;\n    for (int i = 1; i <= 5; i++) {\n        total += i;\n    }\n    printf(\"Sum of 1..5 is: %d\\n\", total);\n    return 0;\n}",
            expectedOutput = "Sum of 1..5 is: 15",
            gotchas = listOf(
                "Missing 'break' in switch cases causes silent fallthrough execution.",
                "Array indices are 0-based; array[size] is out of bounds."
            )
        ),

        // C++ CONCEPTS
        Concept(
            id = "cpp_intro",
            languageId = "cpp",
            title = "Introduction & Modern C++",
            category = "Fundamentals",
            difficulty = "Beginner",
            estimatedMinutes = 6,
            shortSummary = "iostream, std::cout, namespaces, and auto type deduction.",
            detailedExplanation = "C++ began as 'C with Classes' and evolved into a multi-paradigm language with high-level zero-cost abstractions.",
            coreTakeaways = listOf(
                "std::cout with << stream insertion operator replaces printf.",
                "auto keyword deduces types at compile-time without overhead.",
                "Strict type safety and RAII resource management."
            ),
            codeSnippet = "#include <iostream>\n#include <string>\n\nint main() {\n    std::string greeting = \"Hello, C++ Modern World!\";\n    auto rating = 9.8;\n\n    std::cout << greeting << \"\\n\";\n    std::cout << \"Performance: \" << rating << \"/10\\n\";\n    return 0;\n}",
            expectedOutput = "Hello, C++ Modern World!\nPerformance: 9.8/10",
            gotchas = listOf(
                "Prefer '\\n' over std::endl to avoid unnecessary stream flushes.",
                "Avoid 'using namespace std;' in header files."
            )
        ),
        Concept(
            id = "cpp_raii",
            languageId = "cpp",
            title = "RAII & Smart Pointers (unique_ptr)",
            category = "OOP & Memory",
            difficulty = "Advanced",
            estimatedMinutes = 14,
            shortSummary = "Resource Acquisition Is Initialization (RAII), std::unique_ptr, and std::make_unique.",
            detailedExplanation = "RAII binds the lifecycle of a resource to the lifetime of an object. Destructors run automatically when objects go out of scope, eliminating memory leaks.",
            coreTakeaways = listOf(
                "Never write manual 'delete' in modern C++; use smart pointers.",
                "std::unique_ptr represents exclusive ownership; cannot be copied, only moved.",
                "std::shared_ptr enables reference-counted shared ownership."
            ),
            codeSnippet = "#include <iostream>\n#include <memory>\n\nclass Resource {\npublic:\n    Resource() { std::cout << \"[Resource Acquired]\\n\"; }\n    ~Resource() { std::cout << \"[Resource Freed Automatically]\\n\"; }\n    void work() { std::cout << \"Resource doing work\\n\"; }\n};\n\nint main() {\n    {\n        auto ptr = std::make_unique<Resource>();\n        ptr->work();\n    } // Scope ends: automatically destroyed here\n    std::cout << \"Out of scope safely.\\n\";\n    return 0;\n}",
            expectedOutput = "[Resource Acquired]\nResource doing work\n[Resource Freed Automatically]\nOut of scope safely.",
            gotchas = listOf(
                "Attempting to copy a unique_ptr causes compile error; use std::move.",
                "Circular references in std::shared_ptr leak; use std::weak_ptr."
            )
        ),
        Concept(
            id = "cpp_loops",
            languageId = "cpp",
            title = "Range-based for loops & Vectors",
            category = "Control Flow",
            difficulty = "Intermediate",
            estimatedMinutes = 8,
            shortSummary = "std::vector dynamic arrays and range-based for loops.",
            detailedExplanation = "Modern C++ features range-for syntax to iterate safely over standard containers like std::vector, std::map, and arrays.",
            coreTakeaways = listOf(
                "Use const auto& to avoid expensive deep copies during iteration.",
                "std::vector manages dynamic memory on heap automatically.",
                "Algorithms like std::ranges::sort offer expressive operations."
            ),
            codeSnippet = "#include <iostream>\n#include <vector>\n\nint main() {\n    std::vector<int> nums = {10, 20, 30, 40};\n\n    std::cout << \"Values: \";\n    for (const auto& n : nums) {\n        std::cout << n << \" \";\n    }\n    std::cout << \"\\n\";\n    return 0;\n}",
            expectedOutput = "Values: 10 20 30 40",
            gotchas = listOf(
                "Iterating with 'auto x' instead of 'const auto& x' creates unnecessary copies.",
                "Modifying vector elements requires 'auto&' reference binding."
            )
        )
    )

    val comparisons: List<ComparisonItem> = listOf(
        ComparisonItem(
            conceptId = "loops",
            title = "Loops & Iterations",
            description = "Comparing iteration syntax, index control, and performance across C, C++, Java, and Python.",
            cSnippet = "// C (Index Loop)\nint arr[] = {1, 2, 3, 4};\nint len = 4;\nfor (int i = 0; i < len; i++) {\n    printf(\"%d\\n\", arr[i]);\n}",
            cppSnippet = "// C++ (Range-for with const auto&)\nstd::vector<int> arr = {1, 2, 3, 4};\nfor (const auto& item : arr) {\n    std::cout << item << \"\\n\";\n}",
            javaSnippet = "// Java (Enhanced for-each)\nList<Integer> arr = List.of(1, 2, 3, 4);\nfor (int item : arr) {\n    System.out.println(item);\n}",
            pythonSnippet = "# Python (for-in iterable)\narr = [1, 2, 3, 4]\nfor item in arr:\n    print(item)",
            similarities = listOf(
                "All 4 support traditional while(condition) loop constructs.",
                "break and continue behave identically across all four languages.",
                "Each language provides concise collection iteration."
            ),
            differences = listOf(
                "Python uses range() and for-in with no C-style 3-clause for loop.",
                "C requires manual array size tracking via sizeof or integer length.",
                "Java and Python use garbage-collected collections.",
                "C++ allows explicit zero-cost reference iteration (const auto&)."
            ),
            paradigmVerdict = "Python is fastest to write; C++ offers zero-copy memory safety; C provides pure CPU jump efficiency."
        ),
        ComparisonItem(
            conceptId = "hello_world",
            title = "Hello World & Program Entry",
            description = "Comparing runtime entry points, boilerplate, and execution models.",
            cSnippet = "#include <stdio.h>\n\nint main(void) {\n    printf(\"Hello, World!\\n\");\n    return 0;\n}",
            cppSnippet = "#include <iostream>\n\nint main() {\n    std::cout << \"Hello, World!\\n\";\n    return 0;\n}",
            javaSnippet = "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}",
            pythonSnippet = "# Python\nprint(\"Hello, World!\")",
            similarities = listOf(
                "All provide standard output streams (stdout).",
                "C, C++, and Java use main() as the program starting point.",
                "Each provides formatting mechanisms for variables."
            ),
            differences = listOf(
                "Python has zero ceremony: a single top-level line executes immediately.",
                "Java strictly requires an enclosing class and public static void main.",
                "C and C++ return an integer process exit code to the host OS.",
                "C++ uses stream insertion <<, while C uses printf format specifiers."
            ),
            paradigmVerdict = "Python shines for immediate scripting; Java enforces enterprise class discipline; C/C++ interface directly with the OS."
        ),
        ComparisonItem(
            conceptId = "memory",
            title = "Memory & Allocation Model",
            description = "How heap allocation, deallocation, and safety differ across languages.",
            cSnippet = "// C: Manual malloc and free\nint *p = (int*)malloc(sizeof(int));\n*p = 42;\nprintf(\"%d\\n\", *p);\nfree(p); // Manual cleanup required!",
            cppSnippet = "// C++: Modern RAII smart pointers\n#include <memory>\nauto p = std::make_unique<int>(42);\nstd::cout << *p << \"\\n\";\n// Automatically freed when out of scope",
            javaSnippet = "// Java: Managed heap with GC\nInteger p = Integer.valueOf(42);\nSystem.out.println(p);\n// JVM GC cleans up automatically",
            pythonSnippet = "# Python: Dynamic reference count + GC\np = 42\nprint(p)\n# Python GC frees object when refcount is 0",
            similarities = listOf(
                "All store complex data on the computer's heap.",
                "Stack frames manage local function call primitives.",
                "Memory leaks are theoretically possible in all 4 if references linger."
            ),
            differences = listOf(
                "C has zero safety: omitting free causes permanent leaks; double free crashes.",
                "C++ uses deterministic destruction (RAII) at the exact closing brace.",
                "Java relies on generational background GC threads.",
                "Python uses reference counting with a cyclic garbage collector."
            ),
            paradigmVerdict = "C++ RAII provides deterministic cleanup without GC pause times. Python and Java trade speed for memory safety."
        ),
        ComparisonItem(
            conceptId = "classes",
            title = "Classes & OOP Encapsulation",
            description = "Object construction, method binding, and encapsulation models.",
            cSnippet = "// C: Structs + Function pointers\ntypedef struct {\n    char name[32];\n    int age;\n} Person;\n\nvoid Person_init(Person* p, const char* n, int a) {\n    snprintf(p->name, sizeof(p->name), \"%s\", n);\n    p->age = a;\n}",
            cppSnippet = "// C++: Class with access specifiers\nclass Person {\npublic:\n    Person(std::string name, int age)\n        : name_(std::move(name)), age_(age) {}\n    void greet() const { std::cout << \"Hi \" << name_ << \"\\n\"; }\nprivate:\n    std::string name_;\n    int age_;\n};",
            javaSnippet = "// Java: Standard class with private fields\npublic class Person {\n    private String name;\n    private int age;\n    public Person(String name, int age) {\n        this.name = name;\n        this.age = age;\n    }\n    public void greet() { System.out.println(\"Hi \" + name); }\n}",
            pythonSnippet = "# Python: Dynamic class with self\nclass Person:\n    def __init__(self, name: str, age: int):\n        self.name = name\n        self.age = age\n    def greet(self):\n        print(f\"Hi {self.name}\")",
            similarities = listOf(
                "Encapsulate state (fields) alongside behavior (methods).",
                "Constructors initialize state during instantiation.",
                "Support inheritance or composition hierarchies."
            ),
            differences = listOf(
                "C does not have native OOP keywords (no class, private, or inheritance).",
                "Python passes 'self' explicitly as the first argument in every method.",
                "Java enforces strict file-to-class naming parity.",
                "C++ supports multiple inheritance, destructors, and stack-allocated objects."
            ),
            paradigmVerdict = "Java is quintessential OOP; C++ adds destructor power and operator overloading; Python provides rapid duck-typing."
        )
    )

    val quizzes: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "q1",
            title = "Memory Allocation in C",
            languageScope = "c",
            question = "Which C standard library function allocates uninitialized dynamic memory on the heap?",
            codeSnippet = "int *arr = (int*)______(10 * sizeof(int));",
            options = listOf("malloc()", "alloc()", "new", "calloc()"),
            correctIndex = 0,
            explanation = "malloc() allocates specified bytes without zero-initializing them. calloc() initializes to zero, and new belongs to C++."
        ),
        QuizQuestion(
            id = "q2",
            title = "Python Indentation Semantics",
            languageScope = "python",
            question = "What happens if you mix tabs and spaces for indentation in Python 3?",
            codeSnippet = "def check():\n    x = 1\n    return x",
            options = listOf(
                "It automatically converts tabs to spaces",
                "It raises an IndentationError or TabError at compile/parse time",
                "It executes with a runtime warning",
                "It defaults to 2 spaces"
            ),
            correctIndex = 1,
            explanation = "Python 3 strictly disallows mixing tabs and spaces for indentation within the same block, throwing a TabError or IndentationError."
        ),
        QuizQuestion(
            id = "q3",
            title = "Java Equality vs Identity",
            languageScope = "java",
            question = "Which method should you use to check logical value equality between two Java String objects?",
            codeSnippet = "String a = new String(\"hello\");\nString b = new String(\"hello\");\nboolean isEqual = a._____(b);",
            options = listOf("==", "equals()", "compareTo() == 1", "sameAs()"),
            correctIndex = 1,
            explanation = "In Java, == checks memory reference identity (heap address). .equals() compares the actual character contents of the strings."
        ),
        QuizQuestion(
            id = "q4",
            title = "C++ Modern Smart Pointers",
            languageScope = "cpp",
            question = "Which smart pointer represents sole, non-copyable ownership of a heap resource in modern C++?",
            codeSnippet = "std::______<Widget> ptr = std::make_unique<Widget>();",
            options = listOf("shared_ptr", "unique_ptr", "weak_ptr", "auto_ptr"),
            correctIndex = 1,
            explanation = "std::unique_ptr enforces exclusive ownership. Copying is deleted, and ownership can only be transferred using std::move()."
        ),
        QuizQuestion(
            id = "q5",
            title = "Cross-Language Syntax Diff",
            languageScope = "multi",
            question = "Which of the following statements about loops across languages is TRUE?",
            codeSnippet = null,
            options = listOf(
                "Python requires curly braces { } for loop bodies.",
                "C for loops allow range-based iteration over vectors without headers.",
                "Python loops have an optional 'else:' block executed if no break occurred.",
                "Java does not support the break keyword in for-each loops."
            ),
            correctIndex = 2,
            explanation = "Python uniquely supports an optional 'else:' block on for and while loops, which executes only if the loop ran to completion without encountering a break."
        )
    )
}
