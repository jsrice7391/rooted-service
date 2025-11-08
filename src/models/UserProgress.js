const mongoose = require('mongoose');

const userProgressSchema = new mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  dailyContent: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'DailyContent',
    required: true
  },
  completedAt: {
    type: Date,
    default: Date.now
  },
  timeSpent: {
    type: Number, // in seconds
    default: 0
  },
  notes: {
    type: String,
    maxlength: 1000
  },
  reflection: {
    type: String,
    maxlength: 2000
  },
  rating: {
    type: Number,
    min: 1,
    max: 5
  }
}, {
  timestamps: true
});

// Compound index to prevent duplicate completions
userProgressSchema.index({ user: 1, dailyContent: 1 }, { unique: true });
userProgressSchema.index({ user: 1, completedAt: -1 });

module.exports = mongoose.model('UserProgress', userProgressSchema);
