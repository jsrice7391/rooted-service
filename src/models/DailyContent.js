const mongoose = require('mongoose');

const dailyContentSchema = new mongoose.Schema({
  title: {
    type: String,
    required: true,
    trim: true
  },
  content: {
    type: String,
    required: true
  },
  contentType: {
    type: String,
    enum: ['devotional', 'scripture', 'teaching', 'reflection', 'prayer'],
    default: 'devotional'
  },
  theologian: {
    name: String,
    tradition: String,
    bio: String
  },
  scripture: {
    reference: String,
    text: String,
    version: {
      type: String,
      default: 'NIV'
    }
  },
  reflection: {
    question: String,
    prompts: [String]
  },
  tags: [String],
  difficulty: {
    type: String,
    enum: ['beginner', 'intermediate', 'advanced'],
    default: 'beginner'
  },
  estimatedReadTime: {
    type: Number, // in minutes
    default: 5
  },
  publishDate: {
    type: Date,
    default: Date.now
  },
  isPublished: {
    type: Boolean,
    default: true
  },
  createdBy: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }
}, {
  timestamps: true
});

// Index for efficient querying
dailyContentSchema.index({ publishDate: -1 });
dailyContentSchema.index({ tags: 1 });
dailyContentSchema.index({ contentType: 1 });

module.exports = mongoose.model('DailyContent', dailyContentSchema);
