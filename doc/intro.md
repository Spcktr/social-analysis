# Introduction to social-analysis

The loops steps:

1. If the queue is empty, then we've exhausted the part of the graph that's
accessible from the start node. We're done, and we return null to indicate that
we didn't find the node.
2. Otherwise, we pop a path vector off the queue. The current vertex is the last
one.
3. We get the current vertex's neighbors.
4. We remove any vertices that we've already considered.
5. For each neighbor, we append it to the current path vector, creating that
many new path vectors. For example, if the current path vector is [0, 171,
4] and the new neighbors are 7, 42 and 532, then we'll create three new
vectors: [0, 171, 4, 7], [0, 171, 4, 42], and [0, 171, 4, 532].
6. We push each of the new path vectors onto the queue.
7. We add each of the neighbors onto the list of vertices that we've seen.
8. We output the current path to the lazy sequence.
9. Finally, we loop back to step one for the rest of the output sequence.
