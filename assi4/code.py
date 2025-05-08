from datetime import datetime, timedelta

# Helper function to convert HH:MM to total minutes
def time_to_minutes(time_str):
    h, m = map(int, time_str.split(":"))
    return h * 60 + m

# Helper function to convert minutes to HH:MM
def minutes_to_time(minutes):
    h = (minutes // 60) % 24
    m = minutes % 60
    return f"{h:02d}:{m:02d}"

# Node class to store name and time
class Node:
    def __init__(self, name, time_str):
        self.name = name
        self.time_minutes = time_to_minutes(time_str)

# MAIN FUNCTION
def berkeley_algorithm():
    # Input number of nodes
    n = int(input("Enter number of nodes: "))

    nodes = []
    for i in range(n):
        name = f"N{i+1}"
        time_str = input(f"Enter clock time of {name} (HH:MM): ")
        nodes.append(Node(name, time_str))

    # Select master node from input
    master_name = input(f"\nEnter the master node name (e.g., N1 to N{n}): ").strip().upper()
    master_node = next((node for node in nodes if node.name == master_name), None)

    if master_node is None:
        print("Invalid master node name.")
        return

    print(f"\nMaster node is: {master_node.name}\n")

    # Simulate response from nodes (with assumed random delays)
    delays = []
    for node in nodes:
        delay = 0  # You could add simulated delays here if needed
        time_with_delay = node.time_minutes + delay
        delays.append(time_with_delay)
        print(f"{node.name} responds with (after delay): {time_with_delay} minutes")

    # Calculate average time
    average_time = sum(delays) // len(delays)
    print(f"\nAverage Time: {average_time} minutes\n")

    # Calculate and print offsets
    print("Offsets (in minutes):")
    offsets = {}
    for node, delay_time in zip(nodes, delays):
        offset = average_time - delay_time
        offsets[node.name] = offset
        sign = "+" if offset >= 0 else "-"
        print(f"{node.name} Offset: {sign}{abs(offset)} minutes")

    # Correct clocks
    print("\nCorrected Clock Times:")
    for node in nodes:
        corrected_time = node.time_minutes + offsets[node.name]
        print(f"{node.name}: {minutes_to_time(corrected_time)}")

# Run the algorithm
if __name__ == "__main__":
    berkeley_algorithm()


