use std::collections::{HashMap, VecDeque};
use std::hash::Hash;
use std::rc::Rc;

type N<T> = Rc<Node<T>>;
type E<N> = Edge<N>;

#[derive(Debug, Eq, Hash, PartialEq, Clone)]
struct Node<T>
where
    T: Eq,
{
    id: T,
}

#[derive(Debug)]
pub struct Edge<N> {
    pub to: N,
}

#[derive(Debug)]
struct Graph<T>
where
    T: Eq,
{
    vertices: Vec<N<T>>,
    adj: HashMap<N<T>, Vec<E<N<T>>>>,
}

impl Graph<i32> {
    fn add_vertex(&mut self, value: i32) {
        let n = Node::new(value);
        self.vertices.push(n.clone());
        self.adj.insert(n, Vec::new());
    }

    fn add_edge(&mut self, from: i32, to: i32) {
        let from_node = Node::new(from);
        let to_node = Node::new(to);
        self.adj.get_mut(&from_node).unwrap().push(Edge { to: to_node });
    }
}

impl<T: Eq + Clone + Hash> Graph<T> {
    fn new() -> Graph<T> {
        Graph {
            vertices: Vec::new(),
            adj: HashMap::new(),
        }
    }
    fn is_connected(&self, from: T, to: T) -> bool {
        let from_node = Node::new(from);
        let to_node = Node::new(to);
        self.adj.get(&from_node).unwrap().iter().any(|e| e.to == to_node)
    }
    fn get_node(&self, n: T) -> Option<&N<T>> {
        return self.vertices.iter().find(|v| v.id == n);
    }
    fn bfs(&self, start: T) -> Vec<T> {
        let start_node = Node::new(start);
        let mut visited = HashMap::new();
        let mut queue = Vec::new();
        queue.push(start_node.clone());
        visited.insert(start_node.clone(), true);
        let mut result = Vec::new();
        while !queue.is_empty() {
            let current = queue.remove(0);
            result.push(current.id.clone());
            for edge in self.adj.get(&current).unwrap() {
                if !visited.contains_key(&edge.to) {
                    queue.push(edge.to.clone());
                    visited.insert(edge.to.clone(), true);
                }
            }
        }
        result
    }
    fn dfs(&self, start: T) -> Vec<T> {
        let start_node = Node::new(start);
        let mut visited = HashMap::new();
        let mut queue = VecDeque::<Rc<Node<T>>>::new();
        queue.push_front(start_node.clone());
        visited.insert(start_node.clone(), true);
        let mut result = Vec::<T>::new();
        while !queue.is_empty() {
            let current = queue.pop_front().unwrap();
            result.push(current.id.clone());
            for edge in self.adj.get(&current).unwrap() {
                if !visited.contains_key(&edge.to) {
                    queue.push_front(edge.to.clone());
                    visited.insert(edge.to.clone(), true);
                }
            }
        }
        result
    }
}

impl<T: Eq + Clone + Hash> Node<T> {
    fn new(value: T) -> N<T> {
        Rc::new(Node {
            id: value,
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_create() {
        let mut vertices: Vec<N<i32>> = vec![0, 1, 2, 3].iter().map(|i| Node::new(*i)).collect();
        let mut adj: HashMap<N<i32>, Vec<Edge<N<i32>>>> = HashMap::new();
        vertices.iter().for_each(|v| {
            adj.insert(v.clone(), Vec::new());
        });
        let mut g = Graph { vertices, adj };
        g.add_edge(0, 1);
        g.add_edge(0, 2);
        g.add_edge(1, 3);
        println!("{:?}", g);
        assert_eq!(g.is_connected(0, 1), true);
        assert_eq!(g.is_connected(0, 3), false);
    }

    #[test]
    fn test_add_vertex() {
        let mut g = Graph::<i32>::new(); //how to set type here?
        g.add_vertex(0);
        g.add_vertex(1);
        g.add_vertex(2);
        g.add_vertex(3);
        g.add_edge(0, 1);
        g.add_edge(1, 2);
        g.add_edge(1, 4);
        g.add_edge(2, 3);
        assert_eq!(g.is_connected(0, 1), true);
        assert_eq!(g.is_connected(1, 3), false);
        assert_eq!(g.get_node(1), Some(&Node::<i32>::new(1)));
        assert_eq!(g.get_node(5), None);
    }

    #[test]
    fn test_bfs() {
        let mut g = Graph::<i32>::new(); //how to set type here?
        g.add_vertex(0);
        g.add_vertex(1);
        g.add_vertex(2);
        g.add_vertex(3);
        g.add_vertex(4);
        g.add_vertex(5);
        g.add_edge(0, 5);
        g.add_edge(0, 1);
        g.add_edge(1, 2);
        g.add_edge(2, 3);
        g.add_edge(3, 4);
        assert_eq!(g.bfs(0), vec![0, 5, 1, 2, 3, 4]);
    }

    #[test]
    fn test_dfs() {
        let mut g = Graph::<i32>::new(); //how to set type here?
        g.add_vertex(0);
        g.add_vertex(1);
        g.add_vertex(2);
        g.add_vertex(3);
        g.add_vertex(4);
        g.add_vertex(5);
        g.add_edge(0, 5);
        g.add_edge(0, 1);
        g.add_edge(1, 2);
        g.add_edge(2, 3);
        g.add_edge(3, 4);
        assert_eq!(g.dfs(0), vec![0, 1, 2, 3, 4, 5]);
        assert_eq!(g.dfs(3), vec![3, 4, 5]);
    }
}
