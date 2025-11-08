const express = require('express');
const {
  createPost,
  getPosts,
  getPost,
  likePost,
  addComment,
  deletePost
} = require('../controllers/communityController');
const { protect } = require('../middleware/auth');

const router = express.Router();

router.post('/posts', protect, createPost);
router.get('/posts', protect, getPosts);
router.get('/posts/:id', protect, getPost);
router.post('/posts/:id/like', protect, likePost);
router.post('/posts/:id/comments', protect, addComment);
router.delete('/posts/:id', protect, deletePost);

module.exports = router;
