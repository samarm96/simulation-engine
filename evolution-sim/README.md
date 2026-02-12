A simulation that models the lifecycle and evolution of individuals in a simple 2D environment. 

Individuals reproduce, die, forage/consume and have basic locomotion. Reproduction steps can create children with mutations. 

The environment is a simple 2D field that runs a diffusion-reaction system to create and distribute nutrients. 

This project was the first one I worked on after standing up sim-core, and its infrastructure needs to be updated to match the newer orbital mechanics sim. 

Recent changes:
- Added foraging
- Revamped the components
- Revamped the OrganismLifeCycleSystem
- Added a BioModel to hold the base math

TODO: 
- Clean up to conform to current sim-core architecture
- Improve BioModel
- Improve reporting
- Add predation?
- Make the environment more complex?
- Add day/night cycles?
- Add photosynthesis? 