from datetime import datetime, timedelta

def time_to_minutes(time_str):
    h,m=map(int,time_str.split(":"))
    return h*60 + m

def minutes_to_time(minutes):
    h=((minutes)//60)%24
    m=minutes%60
    return f"{h:02d} : {m:02d}"

class Node:
    def __init__(self,name,time_str):
        self.name=name
        self.time_minutes=time_to_minutes(time_str)

def berkley_algorithm():
    n=int(input("enter the nodes:"))
    nodes = []
    for i in range(n):
        name=f"N{i+1}"
        time_str=input(f"Enter clock time of {name} (HH:MM): ")
        nodes.append(Node(name,time_str))

    master_name = input(f"\nEnter the master node name (e.g., N1 to N{n}): ").strip().upper()
    master_node = next((node for node in nodes if node.name==master_name), None)

    if master_node is None:
        print("Invalid master node name.")
        return

    print(f"\nMaster node is: {master_node.name}\n")

    delays = []
    for node in nodes:
        delay=0
        time_with_delay=node.time_minutes+delay
        delays.append(time_with_delay)
        print(f"{node.name} responds with (after delay): {time_with_delay} minutes")

    average_time=sum(delays)//len(delays)
    print(f"\nAverage Time: {average_time} minutes\n")

    print("Offsets in minutes:")
    offsets={}
    for node, delay_time in zip(nodes,delays):
        offset = average_time-delay_time
        offsets[node.name] = offset
        sign = "+" if offset >=0 else "-"
        print(f"{node.name} Offset: {sign}{abs(offset)} minutes")

    # Correct clocks
    print("\nCorrected Clock Times:")
    for node in nodes:
        corrected_time = node.time_minutes + offsets[node.name]
        print(f"{node.name}: {minutes_to_time(corrected_time)}")

# Run the algorithm
if __name__ == "__main__":
    berkley_algorithm()

"""
✅ Example 1 – All clocks are already in sync
Clock Times:

N1: 14:00

N2: 14:00 ← Master

N3: 14:00

Average Time: 14:00

Offsets:

N1: +0 mins

N2: +0 mins

N3: +0 mins

Corrected Times:

All clocks remain 14:00

✅ Example 2 – Leader already has the average time
Clock Times:

N1: 13:30

N2: 14:00 ← Master

N3: 14:30

Average Time: 14:00

Offsets:

N1: +30 mins

N2: +0 mins

N3: -30 mins

Corrected Times:

All clocks adjusted to 14:00

🔁 Example 3 – Leader is behind, adjusts forward
Clock Times:

N1: 13:00

N2: 12:00 ← Master

N3: 13:30

Average Time: 12:50

Offsets:

N1: -10 mins

N2: +50 mins

N3: -40 mins

Corrected Times:

All clocks set to 12:50

⚠️ Example 4 – One clock is far ahead (extreme difference)
Clock Times:

N1: 14:00

N2: 14:01 ← Master

N3: 18:00

Average Time: 15:20

Offsets:

N1: +80 mins

N2: +79 mins

N3: -160 mins

Corrected Times:

All clocks adjusted to 15:20

📉 Example 5 – Master is far ahead, adjusts backward
Clock Times:

N1: 10:00

N2: 11:00

N3: 13:00 ← Master

Average Time: 11:20

Offsets:

N1: +80 mins

N2: +20 mins

N3: -100 mins

Corrected Times:

All clocks adjusted to 11:20"""