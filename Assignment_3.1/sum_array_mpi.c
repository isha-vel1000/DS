#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc, char* argv[]) {
    int rank, size, N;
    int* arr = NULL;
    int* sub_arr = NULL;
    int local_sum = 0, total_sum = 0;

    MPI_Init(&argc, &argv);                        
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);          
    MPI_Comm_size(MPI_COMM_WORLD, &size);          

    if (rank == 0) {
        printf("Enter total number of elements: ");
        scanf("%d", &N);

        // Check if N is divisible by number of processes
        if (N < size) {
            printf("Warning: Number of elements is less than the number of processes. Some processes will have no elements.\n");
        }

        // Allocate and read array
        arr = (int*) malloc(N * sizeof(int));
        printf("Enter %d integers:\n", N);
        for (int i = 0; i < N; i++) {
            scanf("%d", &arr[i]);
        }
    }

    // Broadcast N to all processes
    MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);

    // Determine the number of elements each process will receive
    int elements_per_process = N / size;
    int remainder = N % size;

    // Calculate how many elements each process will get
    int* sendcounts = (int*) malloc(size * sizeof(int));
    int* displs = (int*) malloc(size * sizeof(int));
    int sum = 0;

    // Distribute elements across processes
    for (int i = 0; i < size; i++) {
        sendcounts[i] = elements_per_process + (i < remainder ? 1 : 0);  // distribute remainder
        displs[i] = sum;
        sum += sendcounts[i];
    }

    sub_arr = (int*) malloc(sendcounts[rank] * sizeof(int));

    // Scatter the input array to all processes
    MPI_Scatterv(arr, sendcounts, displs, MPI_INT,
                 sub_arr, sendcounts[rank], MPI_INT,
                 0, MPI_COMM_WORLD);

    // Each process computes the sum of its sub-array
    for (int i = 0; i < sendcounts[rank]; i++) {
        local_sum += sub_arr[i];
    }

    // Gather all partial sums in the root process
    int* partial_sums = NULL;
    if (rank == 0) {
        partial_sums = (int*) malloc(size * sizeof(int));
    }

    // Gather partial sums at the root process (rank 0)
    MPI_Gather(&local_sum, 1, MPI_INT, partial_sums, 1, MPI_INT, 0, MPI_COMM_WORLD);

    if (rank == 0) {
        // Print all partial sums after all processes have completed
        printf("\nPartial sums from all processes:\n");
        for (int i = 0; i < size; i++) {
            printf("Process %d: Partial Sum = %d\n", i, partial_sums[i]);
        }

        // Compute the total sum by summing all the partial sums
        total_sum = 0;
        for (int i = 0; i < size; i++) {
            total_sum += partial_sums[i];
        }

        // Print the total sum at the very end
        printf("\nTotal Sum = %d\n", total_sum);

        // Free the allocated memory
        free(partial_sums);
        free(arr); // Free only in root
    }

    free(sendcounts);
    free(displs);
    free(sub_arr); // Free in all processes
    MPI_Finalize();
    return 0;
}

