✅ 4 Major + 4 Minor Use Cases (Aligned with your current code)
👤 Member 1: Table Manager
Major Use Case:
View Available Tables

Fetches all tables from /tables

Displays availability, capacity

Applies logic to show red/green status

Minor Use Case:
(Optional UI) Highlight unavailable tables with visual cues + errors

Shows errors when trying to book taken tables

Applies simple frontend logic for error display

👤 Member 2: Reservation Handler
Major Use Case:
Make a Reservation

Posts data to /reservations

Validates minimum and maximum duration (30 min to 3 hours)

Checks conflicts before booking

Minor Use Case:
View All Reservations

Fetches and lists data from /reservations

Provides admin or general listing

👤 Member 3: Time/Date Filtering
Major Use Case:
Filter Reservations by Date

Filters /reservations on selected date

Only shows relevant bookings for the selected day

Minor Use Case:
Highlight Reservations Happening Now or Next

Use JS to highlight current/next reservation in frontend

Adds UX benefit without touching backend

👤 Member 4: Admin + Background Tasks
Major Use Case:
Push In-Memory Data to MySQL via Python Worker

Continuously checks /reservations API

Pushes new entries into MySQL

Deduplicates using id tracking

Minor Use Case:
Simple Admin View (admin.html)

Separate HTML page

Could show all reservations + manual sync trigger (optional)

📌 Summary Table

Member	Major Use Case	Minor Use Case
1	View Available Tables	Error display for unavailable tables
2	Make Reservation with Validations	View All Reservations
3	Filter Reservations by Date	Highlight Current/Next Reservations
4	Background Python Sync to MySQL	Admin Page to View/Trigger Sync
