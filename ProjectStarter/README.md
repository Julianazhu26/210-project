# Term Project

## Peronalized Book Management

I plan to create a **book managing system** that mainly provides orgainization of books for those who likes to read. I had this idea because I personally likes to read and record my thoughts and ratings down. Sometimes, popular apps such as Goodreads is overkilling with its system. It contains functions that I never intend to use as well as less freedom to personalize my ratings. Therefore, I believe it would be helpful to create an organization system that I personally enjoy with functions such as 

- Peronalized rating out of 10 and into single digit
- List of book titles
- List of author names
- Thoughts dedicated to each book.

In general, this application should provide a very simple yet useful organization system for users to record their readings. 


## User Stories


- I want to _add names, authors, genre_ of the book when adding them to the to the book lists.
- I would like to _view_ my books within to be read list and read list. 
- I want to be able to _remove_ books within to be read list.
- I want to change _ratings_ of the pre-existing book in the to be read list. 
- I want to be able to _remove_ books within the read list.
- As a user, I want to save my book lists to file when I choose to do so.
- As a user, I want to be able to load my book lists from file.


# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by pressing the add button.
- You can generate the second required action related to the user story "removing multiple Xs from a Y" by pressing the remove button.
- You can locate my visual component by adding a book to the list, then an image will pop up.
- You can save the state of my application by pressing the save button.
- You can reload the state of my application by pressing the load button.

# Phase 4: Task 2
Wed Aug 07 19:36:41 PDT 2024
Added book: b1
Wed Aug 07 19:36:46 PDT 2024
Added book: b2
Wed Aug 07 19:36:51 PDT 2024
Added book: b3
Wed Aug 07 19:36:54 PDT 2024
Removed book: b2

# Phase 4: Task 3

If i had more time, I would try refactoring my project by making interfaces and using the observer and observable design. I probably would make the booklist an interface and contianing one update method, and everytime a book is added to the subject, the booklist will be reminded to be updated. Or, I wil try to make booklist an abstract class, therefore all the lists would inherit certain methods instead of me writing almost identical methods twice for different lists.
