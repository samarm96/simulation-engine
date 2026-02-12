A library that provides the functionality needed to create an ECS program and visualize it. Contains a few implementations of the core libraries (will be moved out in the future).

Modules: 
- evolution-sim : a basic evolution simulation using sim-core
- orbital-mechanics-gui : a GUI built on top of the orbital mechanics simulation, runs the sim and visualizes it
- orbital-mechanics-sim : a simple newtonian gravity simulation, supports 2->N body propagation
- sample-project : an empty project that can be used to stand up new projects quickly
- sim-core : the backbone of this project, provides an abstract ECS architecture that can be used to quickly create and run any type of simulation or game
- sim-gui-core : an abstract GUI library that is built to easily visualize simulations created by sim-core. See orbital-mechanics-gui as an eaxample. Uses LWJGL (https://www.lwjgl.org/) as a dependency to perform visualizations. 